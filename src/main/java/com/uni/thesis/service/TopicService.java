package com.uni.thesis.service;


import com.uni.thesis.model.Topic;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public List<Topic> getAllTopicWithConsultantId(int consultantid){
        return topicRepository.findAllByConsultantId(consultantid);
    }

}
