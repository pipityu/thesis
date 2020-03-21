package com.uni.thesis.service;

import com.uni.thesis.model.Step;
import com.uni.thesis.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;

@Transactional
@Service
public class StepService {

    @Autowired
    StepRepository stepRepository;

    public boolean saveStep(int topicid, String stepname, String stepdescription, String stepdeadline, int steppercentage){
        Step step = new Step();
        step.setTopicid(topicid);
        step.setName(stepname);
        step.setDescription(stepdescription);
        step.setDeadline(LocalDate.parse(stepdeadline));
        step.setPercentage((steppercentage));
        System.out.println(step.toString());
        try{
            stepRepository.save(step);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
