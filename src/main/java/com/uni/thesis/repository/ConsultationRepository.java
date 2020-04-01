package com.uni.thesis.repository;

import com.uni.thesis.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
        List<Consultation> findAllByTopicid(int topicid);
        void deleteAllByTopicid(int topicid);
}
