package com.poc.management.service;

import com.poc.management.dto.PositionDto;
import com.poc.management.util.RestApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PositionService {
    RestApiResponse<PositionDto> addPosition(PositionDto positionDto);

    RestApiResponse<PositionDto> getById(Long id);


    RestApiResponse<Page<PositionDto>> findAllPositions(Pageable pageable);


    RestApiResponse<String> removePosition(Long id);
}
