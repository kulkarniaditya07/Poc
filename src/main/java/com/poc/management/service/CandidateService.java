package com.poc.management.service;

import com.poc.management.dto.CandidateDto;
import com.poc.management.util.RestApiResponse;

public interface CandidateService {
    RestApiResponse<CandidateDto> addCandidate(CandidateDto candidateDto);
}
