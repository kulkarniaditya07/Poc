package com.poc.management.service.impl;

import com.poc.management.dto.CandidateDto;
import com.poc.management.dto.GenderDto;
import com.poc.management.entity.Gender;
import com.poc.management.repository.GenderRepository;
import com.poc.management.service.GenderService;
import com.poc.management.util.PagableObject;
import com.poc.management.util.ResponseUtil;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenderServiceImpl implements GenderService {
    private final PagableObject pagableObject;
    private final GenderRepository genderRepository;

    @Override
    public RestApiResponse<GenderDto> createGender(GenderDto genderDto) {
        Gender gender=pagableObject.map(genderDto,Gender.class);
        Gender saved = genderRepository.saveAndFlush(gender);
        GenderDto savedGender=pagableObject.map(saved, GenderDto.class);
        return ResponseUtil.getResponse(savedGender,"Position Created");
    }

    @Override
    public RestApiResponse<Page<GenderDto>> getGenders(Pageable pageable) {
        Page<Gender> genderPage = genderRepository.findAll(pageable);
        List<GenderDto> genderDtos = pagableObject.mapListToClass(genderPage.getContent(),GenderDto.class);
        Page<GenderDto> pagedCandidateDto= new PageImpl<>(genderDtos,pageable, genderPage.getTotalPages());
        return ResponseUtil.getResponse(pagedCandidateDto,"Genders");
    }
}
