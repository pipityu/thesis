package com.uni.thesis.controller;


import com.uni.thesis.model.Topic;
import com.uni.thesis.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ConsultantController {

    @Autowired
    TopicService topicService;

    @GetMapping("/consultanthome")
    public String chome(Model model){
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId();
        model.addAttribute("allTopic", topicArray);
        return "consultanthome";
    }

    @GetMapping("/choosetopic")
    public String chomewithparam(Model model, @RequestParam (value="topicid",required=false) String topicid ){
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId();
        model.addAttribute("allTopic", topicArray);
        model.addAttribute("topicid", Integer.parseInt(topicid));
        return "consultanthome";
    }

}
