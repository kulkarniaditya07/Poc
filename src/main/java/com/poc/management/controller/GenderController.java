package com.poc.management.controller;

import com.poc.management.dto.GenderDto;
import com.poc.management.entity.Gender;
import com.poc.management.service.GenderService;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genders")
@AllArgsConstructor
public class GenderController {
    private final GenderService genderService;

    @PostMapping
    public ResponseEntity<RestApiResponse<GenderDto>> addGender(@RequestBody GenderDto genderDto){
        return ResponseEntity.status(HttpStatus.OK).body(genderService.createGender(genderDto));
    }


}
