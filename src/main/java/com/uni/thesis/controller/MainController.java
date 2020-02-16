package com.uni.thesis.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/consultantlogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String clogin() {
        return "consultantlogin";
    }

    @RequestMapping(value = "/studentlogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String slogin() {
        return "studentlogin";
    }
    @GetMapping("studenthome")
    public String shome() {
        return "studenthome";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "adminhome";
    }

    /*@RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }*/

}
