package com.uni.thesis.service;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Topic;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.TopicRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

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

/*    public void insertTopic(String consultantid, String name, String description){
        topicRepository.insertTopic(consultantid,name,description);

    }*/


//CÍM ALAPJÁN HA EGYEZÉS VAN AKKOR VISSZATÉRÉS HOGY MÁR VAN ILYEN TÉMA!
    public boolean insertTopic(String username, String topicname, String description){
        Consultant consultant = myUserDetailService.loadConsultant(username);
        Topic topic = new Topic();
        topic.setConsultantid(consultant);
        topic.setName(topicname);
        topic.setDescription(description);
        topic.setStatus("Választható");
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

    public boolean updateTopic(int topicid, String username, String topicname, String description){
        Consultant consultant = myUserDetailService.loadConsultant(username);
        Topic topic = new Topic(topicid, consultant, topicname, description);
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
}
