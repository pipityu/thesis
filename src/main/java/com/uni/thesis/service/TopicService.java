package com.uni.thesis.service;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.model.Topic;
import com.uni.thesis.repository.StudentRepository;
import com.uni.thesis.repository.TopicRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MyUserDetailService myUserDetailService;

    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }

    public List<Topic> getAllTopicWithConsultantId(int consultantid){
        return topicRepository.findAllByConsultantId(consultantid);
    }

    public Topic getTopicById(int id){
        return topicRepository.findTopicByTopicid(id);
    }

    //Service for create a new topic (Consultant)
    public boolean insertTopic(String username, String topicname, String description){
        Consultant consultant = myUserDetailService.loadConsultant(username);
        Topic topic = new Topic();
        topic.setConsultantid(consultant);
        topic.setName(topicname);
        topic.setDescription(description);
        topic.setStatus("választható");
        boolean successTopicInsert = false;
        try{
            topicRepository.save(topic);
            successTopicInsert = true;
        }catch (DataException ex){
            ex.printStackTrace();
        }
        catch (DataIntegrityViolationException de){
            de.printStackTrace();
        }
        return  successTopicInsert;
    }

    //Service for delete a topic (Consultant)
    public boolean deleteTopic(int id){
        boolean successDelete = false;
        try{
            topicRepository.deleteTopicByTopicid(id);
            successDelete = true;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return successDelete;
        }
    }

    //Service for update a topic (Consultant)
    public boolean updateTopic(int topicid, String username, String topicname, String description, String status){
        Consultant consultant = myUserDetailService.loadConsultant(username);
        Topic topic = new Topic(topicid, consultant, topicname, description, status);

        boolean successUpdate = false;

        try{
            topicRepository.save(topic);
            successUpdate = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return successUpdate;
        }
    }

    //Service for trigger when Student select a topic
    public boolean updateStudentSelectedTopic(int topicid, String username, boolean describe){
        boolean flag = false;
        try{
            Student student = myUserDetailService.loadStudent(username);
            Topic topic = topicRepository.findTopicByTopicid(topicid);
            if(describe == false) {
                topic.setStudent(student);
                //should send a request for accept the apply
                topic.setStatus("folyamatban");
                student.setTopicid(topic);
                topicRepository.save(topic);
                studentRepository.save(student);
                flag = true;
            }else{
                topic.setStudent(null);
                topic.setStatus("választható");
                student.setTopicid(null);
                topicRepository.save(topic);
                studentRepository.save(student);
                flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return flag;
        }
    }

    //Service for indicate if a topic is selected or not by a Student
    public boolean isSelectedTopic(String username){
        try{
            Optional<Student> student = studentRepository.findStudentByUsername(username);
            return (student.get().getTopicid()==null) ? true : false;
        }catch (NullPointerException n){
                n.printStackTrace();
        }
        return false;
    }
}
