package com.poc.management.controller;

import com.poc.management.dto.CandidateDto;
import com.poc.management.service.CandidateService;
import com.poc.management.util.RestApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@AllArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<RestApiResponse<CandidateDto>> createPosition(@Valid @RequestBody CandidateDto candidateDto){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.addCandidate(candidateDto));
    }


    @PutMapping("{id}")
    public ResponseEntity<RestApiResponse<String>> patchCandidate(@PathVariable(name = "id") Long id,
                                                 @Parameter(required = false,
                                                     schema = @Schema(implementation = CandidateDto.class))
                                           @Valid  @RequestBody String dto){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.updateCandidate(id, dto));
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<Page<CandidateDto>>> getAllCandidates(@PageableDefault(value = 5)
                                                                                    @SortDefault.SortDefaults({
                                                                                            @SortDefault(value = "id",direction = Sort.Direction.ASC)
                                                                                    }) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.getAllCandidate(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<String>> deleteCandidate(@PathVariable(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(candidateService.deleteCandidate(id));
    }

}
