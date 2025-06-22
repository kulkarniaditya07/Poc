package com.poc.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posi_seq")
    @SequenceGenerator(name = "posi_seq", sequenceName = "position_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "position_name", unique = true, nullable = false,length = 50)
    @NotBlank(message = "Position Name is required")
    private String positionName;


}
