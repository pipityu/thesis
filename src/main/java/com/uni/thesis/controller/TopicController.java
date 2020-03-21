package com.uni.thesis.controller;


import com.uni.thesis.model.Topic;
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




//-------------------------------------------------------CONSULTANT----------------------------------------------------------------------------

    @GetMapping("/consultant/alltopic")
    public String allTopic(Model model){
        List<Topic> topics = topicService.getAllTopic();
        model.addAttribute("topics", topics);
        return "consultant/alltopic";
    }

    @GetMapping("/consultant/choosetopicall")
    public String chooseTopicAll(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);

        return "forward:/consultant/alltopic";
    }

    @GetMapping("/consultant/choosetopic")
    public String chooseTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);

        return "forward:home";
    }

    @GetMapping("/consultant/createtopic")
    public String createtopic(){
        return "consultant/createtopic";
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
        return "redirect:consultant/createtopic";
    }

    @PostMapping("/deletetopic")
    public String deleteTopic(HttpServletRequest request, Model model){
        boolean success = topicService.deleteTopic(Integer.parseInt(request.getParameter("topicid")));
        String successDeleteStr = (success == true)? "Sikeres törlés" : "Hiba a törlésnél";
        model.addAttribute("successDelete", successDeleteStr);
        return "redirect:consultant/home";
    }

    @GetMapping("/consultant/updatetopic")
    public String updateTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        model.addAttribute("topic", topic);
        return "consultant/updatetopic";
    }

    @PostMapping("/updatetopic")
    public String updateTopicSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){
        String topicname = request.getParameter("topicname");
        String description = request.getParameter("description");
        int topicid = Integer.parseInt(request.getParameter("topicid"));

        boolean successUpdate = topicService.updateTopic(topicid, principal.getName(), topicname, description);
        String successUpdateStr = (successUpdate==true) ? "Sikeres módosítás" : "Hiba a módosításnál";

        redirectAttributes.addFlashAttribute("topicUpdateMsg", successUpdateStr);
        return "redirect:consultant/home";
    }

//-------------------------------------------------------STUDENT-----------------------------------------------------------------------------
    @GetMapping("/student/select")
    public String selectTopic(Model model, Principal principal){
        List<Topic> topics = topicService.getAllTopic();
        boolean topicSelected = topicService.isSelectedTopic(principal.getName());
        System.out.println(topicSelected);
        model.addAttribute("topics", topics);
        model.addAttribute("nullTopic", topicSelected);
        return "student/select";
    }

    @GetMapping("/student/selected")
    public String selectedTopic(@RequestParam String topicid, Model model, Principal principal){
        //try
        topicService.updateStudentSelectedTopic(Integer.parseInt(topicid), principal.getName(), false);
        return "forward:/student/select";
    }

    @PostMapping("/describe")
    public String describeTopic(@RequestParam String topicid, Model model, Principal principal){
        //try
        topicService.updateStudentSelectedTopic(Integer.parseInt(topicid), principal.getName(), true);
        return "redirect:/student/select";
    }

    @GetMapping("/student/choosetopic")
    public String chooseTopicByUser(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);
        return "forward:select";
    }


}
