package com.poc.management.controller;

import com.poc.management.dto.CandidateDto;
import com.poc.management.service.CandidateService;
import com.poc.management.util.RestApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidates")
@AllArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;
    @PostMapping
    public ResponseEntity<RestApiResponse<CandidateDto>> createPosition(@Valid @RequestBody CandidateDto candidateDto){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.addCandidate(candidateDto));
    }
}
