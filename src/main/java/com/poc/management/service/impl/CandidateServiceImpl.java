package com.poc.management.service.impl;

import com.poc.management.dto.CandidateDto;
import com.poc.management.entity.Candidates;
import com.poc.management.entity.Positions;
import com.poc.management.repository.CandidateRepository;
import com.poc.management.repository.PositionRepository;
import com.poc.management.service.CandidateService;
import com.poc.management.util.PagableObject;
import com.poc.management.util.ResponseUtil;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final PositionRepository positionRepository;
    private final CandidateRepository candidateRepository;
    private PagableObject pagableObject;

    @Override
    public RestApiResponse<CandidateDto> addCandidate(CandidateDto candidateDto) {
        Candidates candidates=pagableObject.map(candidateDto,Candidates.class);
        if (candidateDto.getPositionId() != null && !candidateDto.getPositionId().isEmpty()) {
            List<Positions> positions = positionRepository.findAllById(candidateDto.getPositionId());
            candidates.setPositions(positions);
        }
        Candidates candidates1 = candidateRepository.saveAndFlush(candidates);
        CandidateDto candidatesDto=pagableObject.map(candidates1,CandidateDto.class);
        return ResponseUtil.getResponse(candidatesDto,"Candidate Created");
    }






}
