package com.poc.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq")
   @SequenceGenerator(name = "gen_seq",sequenceName = "gender_sequence",allocationSize = 1)
    private Long id;

    private String genderType;
}
