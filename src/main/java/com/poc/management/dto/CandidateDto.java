package com.poc.management.dto;

import com.poc.management.annotations.LegalAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Email(message = "Provide valid email", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @LegalAge
    private LocalDate dob;
    private List<Long> positionId;
}
