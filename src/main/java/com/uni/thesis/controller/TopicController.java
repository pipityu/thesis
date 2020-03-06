package com.uni.thesis.controller;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Topic;
import com.uni.thesis.service.MyUserDetailService;
import com.uni.thesis.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("/alltopic")
    public String allTopic(Model model){
        List<Topic> topics = topicService.getAllTopic();
        model.addAttribute("topics", topics);
        return "/consultant/alltopic";
    }

    @GetMapping("/choosetopicall")
    public String chooseTopicAll(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);

        return "forward:/alltopic";
    }

    @GetMapping("/choosetopic")
    public String chooseTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);

        return "forward:/home";
    }

    @GetMapping("/createtopic")
    public String createtopic(){
        return "/consultant/createtopic";
    }

//Teljes objektumot ad vissza
/*    @PostMapping("/createtopic")
    public String topicSubmit(HttpServletRequest request, @ModelAttribute Topic topic){
        String topicname = request.getParameter("topicname");
        String description = request.getParameter("deascription");
       // model.addAttribute("tname", topicname);
        return "/consultant/createtopic";
    }*/


    @PostMapping("/createtopic")
    public String topicSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){

        String topicname = request.getParameter("topicname");
        String description = request.getParameter("description");

        boolean success = topicService.insertTopic(principal.getName(), topicname, description);
        String successInsertStr = (success == true)? "Sikeres feltöltés" : "Hiba a feltöltésnél";
        redirectAttributes.addFlashAttribute("successInsert", successInsertStr);
        return "redirect:/createtopic";
    }

    @PostMapping("/deletetopic")
    public String deleteTopic(HttpServletRequest request, Model model){
        boolean success = topicService.deleteTopic(Integer.parseInt(request.getParameter("topicid")));
        String successDeleteStr = (success == true)? "Sikeres törlés" : "Hiba a törlésnél";
        model.addAttribute("successDelete", successDeleteStr);
        return "redirect:/home";
    }

    @GetMapping("/updatetopic")
    public String updateTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        model.addAttribute("topic", topic);
        return "/consultant/updatetopic";
    }

    @PostMapping("/updatetopic")
    public String updateTopicSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){
        String topicname = request.getParameter("topicname");
        String description = request.getParameter("description");
        int topicid = Integer.parseInt(request.getParameter("topicid"));

        boolean successUpdate = topicService.updateTopic(topicid, principal.getName(), topicname, description);
        String successUpdateStr = (successUpdate==true) ? "Sikeres módosítás" : "Hiba a módosításnál";

        redirectAttributes.addFlashAttribute("message", successUpdateStr);
        return "redirect:/home";
    }


}
