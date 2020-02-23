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
    public String clogin() {
        return "login";
    }
}
