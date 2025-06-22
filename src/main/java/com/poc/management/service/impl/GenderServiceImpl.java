package com.poc.management.service.impl;

import com.poc.management.dto.GenderDto;
import com.poc.management.entity.Gender;
import com.poc.management.repository.GenderRepository;
import com.poc.management.service.GenderService;
import com.poc.management.util.PagableObject;
import com.poc.management.util.ResponseUtil;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
