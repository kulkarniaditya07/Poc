package com.poc.management.controller;

import com.poc.management.dto.GenderDto;
import com.poc.management.entity.Gender;
import com.poc.management.service.GenderService;
import com.poc.management.util.RestApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genders")
@AllArgsConstructor
public class GenderController {
    private final GenderService genderService;

    @PostMapping
    public ResponseEntity<RestApiResponse<GenderDto>> addGender(@RequestBody GenderDto genderDto){
        return ResponseEntity.status(HttpStatus.OK).body(genderService.createGender(genderDto));
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<Page<GenderDto>>> getAllGender(@PageableDefault(size = 5)
                                                                         @SortDefault.SortDefaults({
                                                                                 @SortDefault(value = "id",direction = Sort.Direction.ASC)
                                                                         })Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(genderService.getGenders(pageable));
    }

}
