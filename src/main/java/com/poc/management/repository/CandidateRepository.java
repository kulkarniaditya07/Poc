package com.poc.management.repository;

import com.poc.management.entity.Candidates;
import com.poc.management.entity.Positions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidates, Long> {

    boolean existsByPositionsContaining(Positions position);


}
