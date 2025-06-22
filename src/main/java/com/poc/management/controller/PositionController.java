package com.poc.management.controller;

import com.poc.management.dto.PositionDto;
import com.poc.management.service.PositionService;
import com.poc.management.util.RestApiResponse;
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

@RestController
@RequestMapping("/positions")
@AllArgsConstructor
public class PositionController {

    private PositionService positionService;

    @PostMapping
    public ResponseEntity<RestApiResponse<PositionDto>> createPosition(@Valid @RequestBody PositionDto positionDto){
        return ResponseEntity.status(HttpStatus.OK).body(positionService.addPosition(positionDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<PositionDto>> findPosition(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(positionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<Page<PositionDto>>> getAllPositions(@PageableDefault(value = 5)
                                                              @SortDefault.SortDefaults({
                                                                      @SortDefault(value = "id",direction = Sort.Direction.ASC)
                                                              })Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(positionService.findAllPositions(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<String>> deletePosition(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(positionService.removePosition(id));
    }


}
