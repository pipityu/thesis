package com.uni.thesis.repository;

import com.uni.thesis.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query(value = "select * from topic where consultantid = 1;", nativeQuery = true)
    List<Topic> findAllByConsultantId();

}
