package com.poc.management.service;

import com.poc.management.dto.CandidateDto;
import com.poc.management.util.RestApiResponse;
import jakarta.validation.Valid;

public interface CandidateService {
    RestApiResponse<CandidateDto> addCandidate(CandidateDto candidateDto);

    RestApiResponse<String> updateCandidate(Long id, String candidateDto);
}
