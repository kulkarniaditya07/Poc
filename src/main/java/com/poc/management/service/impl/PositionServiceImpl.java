package com.poc.management.service.impl;

import com.poc.management.dto.PositionDto;
import com.poc.management.entity.Positions;
import com.poc.management.exceptions.RestApiException;
import com.poc.management.repository.CandidateRepository;
import com.poc.management.repository.PositionRepository;
import com.poc.management.service.PositionService;
import com.poc.management.util.PagableObject;
import com.poc.management.util.ResponseUtil;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionServiceImpl implements PositionService {
    private PagableObject pagableObject;
    private PositionRepository positionRepository;
    private CandidateRepository candidateRepository;

    @Override
    public RestApiResponse<PositionDto> addPosition(PositionDto positionDto) {
        Positions positions=pagableObject.map(positionDto,Positions.class);
        Positions saved = positionRepository.saveAndFlush(positions);
        PositionDto savedPosition=pagableObject.map(saved, PositionDto.class);
        return ResponseUtil.getResponse(savedPosition,"Position Created");
    }

    @Override
    public RestApiResponse<PositionDto> getById(Long id) {
        Positions positions=positionRepository.findById(id)
                .orElseThrow(()-> new RestApiException(String.format("Position with id: %s does not exists",id), HttpStatus.BAD_REQUEST));
        PositionDto positionDto=pagableObject.map(positions, PositionDto.class);
        return ResponseUtil.getResponse(positionDto,"Position");
    }

    @Override
    public RestApiResponse<Page<PositionDto>> findAllPositions(Pageable pageable) {
        Page<Positions> positionsPage = positionRepository.findAll(pageable);
        List<PositionDto> positionDtos= pagableObject.mapListToClass(positionsPage.getContent(),PositionDto.class);
        Page<PositionDto> pagedPositionDto= new PageImpl<>(positionDtos,pageable, positionsPage.getTotalPages());
        return ResponseUtil.getResponse(pagedPositionDto,"Positions");
    }

    @Override
    public RestApiResponse<String> removePosition(Long id) {
        return ResponseUtil.getResponseMessage(positionRepository.findById(id)
                .map(position -> {
                    if (candidateRepository.existsByPositionsContaining(position)) {
                        throw new RestApiException(
                                "Cannot delete this position because candidates have already applied",
                                HttpStatus.BAD_REQUEST);
                    }
                    positionRepository.delete(position);
                    return "Position Deleted";
                })
                .orElseThrow(() -> new RestApiException(
                        String.format("Position with id: %s not found", id),
                        HttpStatus.BAD_REQUEST)));
    }


    private RestApiResponse<String> removePosition_Try1(Long id) {
        Positions positions=positionRepository.findById(id)
                .orElseThrow(()-> new RestApiException(String.format("Position with id: %s not found",id),HttpStatus.BAD_REQUEST));

        boolean isCandidatePresent=candidateRepository.existsByPositionsContaining(positions);
        if(isCandidatePresent){
            throw new RestApiException("Cannot delete this position because candidates have already applied",HttpStatus.BAD_REQUEST);
        }
        positionRepository.delete(positions);
        return ResponseUtil.getResponseMessage("Position deleted");
    }

}
