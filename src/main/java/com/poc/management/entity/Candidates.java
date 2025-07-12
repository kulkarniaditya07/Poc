package com.poc.management.entity;

import com.poc.management.annotations.LegalAge;
import com.poc.management.enums.JoiningType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Candidates {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "can_seq")
    @SequenceGenerator(name = "can_seq", sequenceName = "candidate_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Provide valid email", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Column(nullable = false)
    @LegalAge
    private LocalDate dob;

    @ManyToMany
    @JoinTable(
            name = "candidates_positions",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private List<Positions> positions;


    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private JoiningType joiningType;
}
