package com.uni.thesis.controller;


import com.uni.thesis.model.Topic;
import com.uni.thesis.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ConsultantController {

    @Autowired
    TopicService topicService;

    @GetMapping("home")
    public String home(HttpServletRequest request){
        return (request.isUserInRole("CONSULTANT")) ? "consultant/home" : "student/home";
    }

    @GetMapping("/consultanthome")
    public String chome(Model model){
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId();
        model.addAttribute("allTopic", topicArray);
        return "consultanthome";
    }


    @GetMapping("/createtopic")
    public String createtopic(){
        return "createtopic";
    }

}
