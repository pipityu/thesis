package com.uni.thesis.controller;


import com.uni.thesis.model.Topic;
import com.uni.thesis.service.ConsultationService;
import com.uni.thesis.service.StepService;
import com.uni.thesis.service.TopicService;
import com.uni.thesis.service.UserService;
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
    @Autowired
    ConsultationService consultationService;
    @Autowired
    StepService stepService;
    @Autowired
    UserService userService;




//-------------------------------------------------------CONSULTANT----------------------------------------------------------------------------

    //Consultant can check all the submitted topics(own and anothers)
    @GetMapping("/consultant/alltopic")
    public String allTopic(Model model){
        List<Topic> topics = topicService.getAllTopic();
        model.addAttribute("topics", topics);
        return "consultant/alltopic";
    }

    //Describe the selected topic in the all-topic category
    @GetMapping("/consultant/choosetopicall")
    public String chooseTopicAll(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);

        return "forward:/consultant/alltopic";
    }

    //Describe the selected topic in the own-topic category
    @GetMapping("/consultant/choosetopic")
    public String chooseTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);
        return "forward:home";
    }

    //Consultant create a topic
    @GetMapping("/consultant/createtopic")
    public String createtopic(){
        return "consultant/createtopic";
    }


    //(Consultant) Handles the create topic process
    @PostMapping("/createtopic")
    public String topicSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){
        String topicname = request.getParameter("topicname");
        String description = request.getParameter("description");
        boolean success = topicService.insertTopic(principal.getName(), topicname, description);
        String successInsertStr = (success == true)? "Sikeres feltöltés" : "Hiba a feltöltésnél";
        redirectAttributes.addFlashAttribute("successInsert", successInsertStr);
        return "redirect:consultant/createtopic";
    }

    //JELENLEG A FOREIGN KEY NEM ENGEDI TÖRÖLNI HA FEL VAN VÉVE HALLGATÓNÁL A TÉMA!
    //HA mindenkepp törölni kell akkor eloszor a hallgatotol is törölni kell a temat!!
    //(Consultant) Handles the delete topic process
    @PostMapping("/deletetopic")
    public String deleteTopic(HttpServletRequest request){
        consultationService.deleteConsultationByTopicId(Integer.parseInt(request.getParameter("topicid")));
        stepService.deleteAllstepByTopicId(Integer.parseInt(request.getParameter("topicid")));
        topicService.deleteTopic(Integer.parseInt(request.getParameter("topicid")));
        return "redirect:consultant/home";
    }

    //Consultant updates a topic
    @GetMapping("/consultant/updatetopic")
    public String updateTopic(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        model.addAttribute("topic", topic);
        return "consultant/updatetopic";
    }

    //(Consultant) Handles the update topic process
    @PostMapping("/updatetopic")
    public String updateTopicSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){
        String topicname = request.getParameter("topicname");
        String description = request.getParameter("description");
        int topicid = Integer.parseInt(request.getParameter("topicid"));
        String status = request.getParameter("status");
        boolean successUpdate = topicService.updateTopic(topicid, principal.getName(), topicname, description, status);
        String successUpdateStr = (successUpdate==true) ? "Sikeres módosítás" : "Hiba a módosításnál";
        redirectAttributes.addFlashAttribute("topicUpdateMsg", successUpdateStr);
        return "redirect:consultant/home";
    }

//-------------------------------------------------------STUDENT-----------------------------------------------------------------------------

    //Student can select from the topics
    @RequestMapping(value = "/student/select", method = {RequestMethod.GET, RequestMethod.POST})
    public String selectTopic(Model model, Principal principal){
        List<Topic> topics = topicService.getAllTopic();
        boolean topicSelected = topicService.isSelectedTopic(principal.getName());
        model.addAttribute("topics", topics);
        model.addAttribute("nullTopic", topicSelected);
        return "student/select";
    }

    //(Student) Handles the selected topic process
    @PostMapping("/student/selected")
    public String selectedTopic(@RequestParam String topicid, Principal principal){
        topicService.updateStudentSelectedTopic(Integer.parseInt(topicid), principal.getName(), false);
        return "redirect:/student/select";
    }

    //(Student) Handles the describe topic process
    @PostMapping("/describe")
    public String describeTopic(@RequestParam String topicid, Principal principal){
        userService.studentUpdate(Integer.parseInt(topicid));
        stepService.deleteAllstepByTopicId(Integer.parseInt(topicid));
        consultationService.deleteConsultationByTopicId(Integer.parseInt(topicid));
        topicService.updateStudentSelectedTopic(Integer.parseInt(topicid), principal.getName(), true);
        return "redirect:/student/select";
    }

    //(Student) Describe the selected topic from all-topic
    @GetMapping("/student/choosetopic")
    public String chooseTopicByUser(@RequestParam String topicid, Model model){
        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        String desc = topic.getDescription();
        model.addAttribute("description", desc);
        return "forward:select";
    }


}
