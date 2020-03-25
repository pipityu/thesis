package com.uni.thesis.service;

import com.uni.thesis.model.Step;
import com.uni.thesis.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StepService {

    @Autowired
    StepRepository stepRepository;

    //Service for save a step for a topic (Student)
    public boolean saveStep(int topicid, int stepstatus, String stepname, String stepdescription, String stepdeadline, int steppercentage){
        Step step = new Step();
        step.setTopicid(topicid);
        step.setName(stepname);
        step.setDescription(stepdescription);
        step.setDeadline(LocalDate.parse(stepdeadline));
        step.setPercentage((steppercentage));
        step.setStepstatus(stepstatus);
        System.out.println(step.toString());
        try{
            stepRepository.save(step);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Service for get all steps for a topic (Student and Consultant also)
    public List<Step> findAllStepsByTopicId(int topicid){
        return stepRepository.findAllByTopicid(topicid);
    }

    //Service for delete a step (Student)
    public void deleteStep(int stepid){
        stepRepository.deleteById(stepid);
    }

    //Service for delete all step by topicid, triggered when a topic is described or deleted
    public void deleteAllstepByTopicId(int topicid){
        stepRepository.deleteAllByTopicid(topicid);
    }

    //Service for indicate when a step is done (Student)
    public void doneStep(int stepid){
        Optional<Step> step = stepRepository.findById(stepid);
        step.get().setStepstatus(1);
        stepRepository.save(step.get());
    }

}
