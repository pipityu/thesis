package com.uni.thesis.repository;

import com.uni.thesis.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query(value = "select * from topic where consultantid = ?1", nativeQuery = true)
    List<Topic> findAllByConsultantId(int consultantid);

    Topic findTopicByTopicid(int id);



/*    @Query(value = "insert into topic values(default, ?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertTopic(String consultantid, String name, String description);*/

    void deleteTopicByTopicid(int id);

}
