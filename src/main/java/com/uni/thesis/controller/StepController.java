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

    //Student create a new step for a topic
    @PostMapping("/student/newstep")
    public String newStep(@RequestParam int topicid, int stepstatus, String stepname, String stepdescription, String stepdeadline, int steppercentage, Model model){
        boolean success = stepService.saveStep(topicid, stepstatus, stepname, stepdescription, stepdeadline, steppercentage);
        model.addAttribute("successNewStep", success);
        return "redirect:home";
    }

    //Student delete a step for a topic
    @GetMapping("/student/deletestep")
    public String deleteStep(@RequestParam int stepid){
        stepService.deleteStep(stepid);
        return "redirect:home";
    }

    //Student indicates that the step is already done
    @GetMapping("/student/donestep")
    public String doneStep(@RequestParam int stepid){
        stepService.doneStep(stepid);
        return "redirect:home";
    }

}
