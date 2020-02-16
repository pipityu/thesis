package com.uni.thesis.repository;

import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsultantRepository extends JpaRepository<Consultant, Integer> {
    Optional<Consultant> findConsultantByUsername(String username); //Optional egy konténer objektum ami null vagy nem null értéket tárol


}
