package com.uni.thesis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        return "login";
    }
    @RequestMapping(value = "/informations", method = RequestMethod.GET)
    public String informations(){
        return "/informations";
    }
    @RequestMapping("/registration")
    public String registration(){return "/registration";}
    @GetMapping("/consultantreg")
    public String consultantreg(){return "/consultantreg";}
    @GetMapping("/studentreg")
    public String studentreg(){return "/studentreg";}

}
