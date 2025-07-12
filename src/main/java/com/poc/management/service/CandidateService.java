package com.poc.management.service;

import com.poc.management.dto.CandidateDto;
import com.poc.management.util.RestApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CandidateService {
    RestApiResponse<CandidateDto> addCandidate(CandidateDto candidateDto);

    RestApiResponse<String> updateCandidate(Long id, String candidateDto);

    RestApiResponse<Page<CandidateDto>> getAllCandidate(Pageable pageable);


    RestApiResponse<String> deleteCandidate(Long id);
}
