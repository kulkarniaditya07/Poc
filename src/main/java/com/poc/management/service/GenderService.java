package com.poc.management.service;

import com.poc.management.dto.GenderDto;
import com.poc.management.util.RestApiResponse;

public interface GenderService {
    RestApiResponse<GenderDto> createGender(GenderDto genderDto);
}
