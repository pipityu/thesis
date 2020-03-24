package com.uni.thesis.controller;


import com.uni.thesis.model.Consultation;
import com.uni.thesis.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ConsultationController {

    @Autowired
    ConsultationService consultationService;


    @RequestMapping(value = "/student/consultation", method = {RequestMethod.GET,RequestMethod.POST})
    public String consultation(@RequestParam int topicid, String success, Model model){
        model.addAttribute("topicid", topicid);
        List<Consultation> consultations = consultationService.getAllConsultationByTopicid(topicid);
        model.addAttribute("consultations", consultations);
        model.addAttribute("success", success);
        return "student/consultation";
    }

    @PostMapping("/student/consultationrequest")
    public String consultationRequest(@RequestParam int topicid, String name, String description, String time, RedirectAttributes redirectAttributes){
        boolean success = consultationService.sendConsultationRequest(topicid, name, description, time);
        redirectAttributes.addAttribute("topicid", topicid);
        redirectAttributes.addAttribute("success", success);
        return "redirect:consultation";
    }

    @PostMapping("/student/consultationupdate")
    public String consultationUpdate(@RequestParam int topicid, int consultationid, String name, String description, RedirectAttributes redirectAttributes){
        boolean success = consultationService.consultationUpdate(consultationid, name, description);
        redirectAttributes.addAttribute("topicid", topicid);
        redirectAttributes.addAttribute("success", success);
        return "redirect:consultation";
    }

    @PostMapping("/acceptrequest")
    public String acceptRequest(@RequestParam int consultationid, int topicid, RedirectAttributes redirectAttributes){
        consultationService.acceptConsultation(consultationid);
        redirectAttributes.addAttribute("topicid", topicid);
        return "redirect:consultant/studentdetails";
    }

    @PostMapping("/refuserequest")
    public String refuseRequest(@RequestParam int consultationid,  int topicid, RedirectAttributes redirectAttributes){
        consultationService.refuseConsultation(consultationid);
        redirectAttributes.addAttribute("topicid", topicid);
        return "redirect:consultant/studentdetails";
    }

    @PostMapping("/student/deleterequest")
    public String deleteRequest(@RequestParam int consultationid, int topicid, RedirectAttributes redirectAttributes){
        consultationService.deleteRequest(consultationid);
        redirectAttributes.addAttribute("topicid", topicid);
        return "redirect:consultation";
        //return "redirect:consultation?topicid="+topicid;     EZ IS EGY OPCIO
    }

}
