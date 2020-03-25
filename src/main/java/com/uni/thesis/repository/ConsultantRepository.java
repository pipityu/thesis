package com.uni.thesis.repository;

import com.uni.thesis.model.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultantRepository extends JpaRepository<Consultant, Integer> {
    Optional<Consultant> findConsultantByUsername(String username);
}
