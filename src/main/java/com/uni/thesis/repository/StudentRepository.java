package com.uni.thesis.repository;

import com.uni.thesis.model.Student;
import com.uni.thesis.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findStudentByUsername(String username);

    Optional<Student> findStudentByTopicid(Topic topicid);
}
