package com.poc.management.service;

import com.poc.management.dto.GenderDto;
import com.poc.management.util.RestApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenderService {
    RestApiResponse<GenderDto> createGender(GenderDto genderDto);

    RestApiResponse<Page<GenderDto>> getGenders(Pageable pageable);
}
