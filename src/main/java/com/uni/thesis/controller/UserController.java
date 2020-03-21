package com.uni.thesis.controller;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.model.Topic;
import com.uni.thesis.service.MyUserDetailService;
import com.uni.thesis.service.TopicService;
import com.uni.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    TopicService topicService;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    UserService userService;

//LOGIN SPRING BOOT ALTAL: ONNAN SIKER ESETEN /home -RA DOB


    @GetMapping("home")
    public String home(HttpServletRequest request){
        return (request.isUserInRole("CONSULTANT")) ? "forward:consultant/home" : "forward:student/home";
    }

    @GetMapping("/consultant/home")
    public String consultanthome(Model model, Principal principal){
        Consultant consultant = myUserDetailService.loadConsultant(principal.getName());
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId(consultant.getConsultantid());
        model.addAttribute("allTopic", topicArray);
        return "consultant/home";
    }

    @GetMapping("/student/home")
    public String studenthome(Model model, Principal principal){
        Student student = myUserDetailService.loadStudent(principal.getName());
        Boolean nullMsg;
        try{
            Topic topic = topicService.getTopicById(student.getTopicid().getTopicid());
            nullMsg = false;
            model.addAttribute("topic", topic);
            model.addAttribute("nullMsg", nullMsg);
        }catch(NullPointerException n){
            nullMsg = true;
            model.addAttribute("nullMsg", nullMsg);
        }

        return "student/home";
    }


    //------------------------------------REGISTRATION------------------------------------------
    //CONSULTANT
    @PostMapping("/consultantreg")
    public String consultantReg(@RequestParam String username, String name, String email, String password, Model model){
        String success = userService.consultantReg(username,name,email,password);
        model.addAttribute("regSuccess", success);
        return (success == "true") ? "forward:login" : "/consultantreg";
    }

}
