package com.uni.thesis.service;


import com.uni.thesis.model.Consultation;
import com.uni.thesis.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ConsultationService {

    @Autowired
    ConsultationRepository consultationRepository;

    //Service for Student send a consultation request
    public boolean sendConsultationRequest(int topicid, String name, String description, String time){
        List<Consultation> consultations = consultationRepository.findAllByTopicid(topicid);
        for(Consultation c : consultations){
            if(c.getStatus().compareTo("Elfogadásra vár") == 0){
                return false;
            }
            System.out.println(c.getStatus());
        }
        try{
            Consultation consultation = new Consultation();
            consultation.setTopicid(topicid);
            consultation.setName(name);
            consultation.setDescription(description);
            consultation.setStatus("Elfogadásra vár");
            consultation.setTime(LocalDate.parse(time));
            consultationRepository.save(consultation);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Service for delete a consultation (Student and Consultant also)
    public void deleteConsultationByTopicId(int topicid){
        consultationRepository.deleteAllByTopicid(topicid);
    }

    //Service for get all consultation (Student and Consultant also)
    public List<Consultation> getAllConsultationByTopicid(int topicid){
        return consultationRepository.findAllByTopicid(topicid);
    }

    //Service for update a consultation (Student)
    public boolean consultationUpdate(int consultationid, String name, String description){
        try{
            Optional<Consultation> con = consultationRepository.findById(consultationid);
            Consultation consultation = con.get();
            consultation.setName(name);
            consultation.setDescription(description);
            System.out.println(consultation.getConsultationid()+","+consultation.getTopicid()+","+consultation.getDescription()+","+consultation.getStatus());
            consultationRepository.save(consultation);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Service for accept a request for a consultation (Consultant)
    public boolean acceptConsultation(int consultationid){
        Optional<Consultation> consultation = consultationRepository.findById(consultationid);
        if(consultation.isPresent()){
            consultation.get().setStatus("Elfogadva");
            return true;
        }
        return false;
    }

    //Service for refuse a request for a consultation (Consultant)
    public boolean refuseConsultation(int consultationid){
        Optional<Consultation> consultation = consultationRepository.findById(consultationid);
        if(consultation.isPresent()){
               consultation.get().setStatus("Elutasítva");
               return true;
        }
        return false;
    }

    //Service for delete request for a consultation (Student and Consultant also)
    public void deleteRequest(int consultationid){
        consultationRepository.deleteById(consultationid);
    }
}
