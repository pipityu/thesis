package com.uni.thesis.repository;


import com.uni.thesis.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Integer> {

    public List<Step> findAllByTopicid(int topicid);
    void deleteAllByTopicid(int topicid);
}
