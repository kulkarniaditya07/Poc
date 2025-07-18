package com.poc.management.dto;

import com.poc.management.enums.JoiningType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private List<Long> positionId;
    private Long genderId;
    private JoiningType joiningType;
}
