package com.uni.thesis.controller;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.model.Topic;
import com.uni.thesis.service.MyUserDetailService;
import com.uni.thesis.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    TopicService topicService;

    @Autowired
    MyUserDetailService myUserDetailService;


    @GetMapping("home")
    public String home(HttpServletRequest request){
        return (request.isUserInRole("CONSULTANT")) ? "forward:consultant/home" : "forward:student/home";
    }

    @GetMapping("consultant/home")
    public String consultanthome(Model model, Principal principal){
        Consultant consultant = myUserDetailService.loadConsultant(principal.getName());
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId(consultant.getConsultantid());
        model.addAttribute("allTopic", topicArray);
        return "consultant/home";
    }

    @GetMapping("student/home")
    public String studenthome(Principal principal){

        return "student/home";
    }



}
