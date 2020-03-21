package com.uni.thesis.controller;


import com.uni.thesis.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StepController {

    @Autowired
    StepService stepService;

    @GetMapping("/student/newstep")
    public String newStep(@RequestParam int topicid, String stepname, String stepdescription, String stepdeadline, int steppercentage, Model model){
        boolean success = stepService.saveStep(topicid, stepname, stepdescription, stepdeadline, steppercentage);
        boolean nullMsg = false;
        model.addAttribute("successNewStep", success);
        return "forward:/student/home";
    }

}
