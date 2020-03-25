package com.uni.thesis.controller;


import com.uni.thesis.model.*;
import com.uni.thesis.repository.ConsultationRepository;
import com.uni.thesis.service.MyUserDetailService;
import com.uni.thesis.service.StepService;
import com.uni.thesis.service.TopicService;
import com.uni.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    TopicService topicService;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    UserService userService;

    @Autowired
    StepService stepService;

    @Autowired
    ConsultationRepository consultationRepository;


//LOGIN spring boot config: siker-> /home
    @GetMapping("home")
    public String home(HttpServletRequest request){
        return (request.isUserInRole("CONSULTANT")) ? "forward:consultant/home" : "forward:student/home";
    }

    //Consultant Login
    @GetMapping("/consultant/home")
    public String consultanthome(Model model, Principal principal){
        Consultant consultant = myUserDetailService.loadConsultant(principal.getName());
        List<Topic> topicArray = topicService.getAllTopicWithConsultantId(consultant.getConsultantid());
        model.addAttribute("allTopic", topicArray);
        return "consultant/home";
    }

    //Student Login
    @RequestMapping(value = "/student/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String studenthome(Model model, Principal principal){
        Student student = myUserDetailService.loadStudent(principal.getName());
        Boolean nullMsg;
        try{
            Topic topic = topicService.getTopicById(student.getTopicid().getTopicid());
            nullMsg = false;
            model.addAttribute("topic", topic);
            model.addAttribute("nullMsg", nullMsg);
            List<Step>steps = stepService.findAllStepsByTopicId(topic.getTopicid());
            int stepnumber = steps.size();
            int sumpercentage = 0;
            int stepDone = 0;
            int sumDonePercentage = 0;
            for (Step s: steps) {
                sumpercentage+=s.getPercentage();
                if(s.getStepstatus() == 1){
                    stepDone++;
                    sumDonePercentage += s.getPercentage();
                }
            }
            model.addAttribute("stepnumber", stepnumber);
            model.addAttribute("sumpercentage", sumpercentage);
            model.addAttribute("stepDone", stepDone);
            model.addAttribute("sumDonePercentage", sumDonePercentage);
            model.addAttribute("steps", steps);
        }catch(NullPointerException n){
            nullMsg = true;
            model.addAttribute("nullMsg", nullMsg);
        }
        return "student/home";
    }

    //Consultant registration
    @PostMapping("/consultantreg")
    public String consultantReg(@RequestParam String username, String name, String email, String password, Model model){
        String success = userService.consultantReg(username,name,email,password);
        model.addAttribute("regSuccess", success);
        return (success == "true") ? "forward:login" : "consultantreg";
    }

    //Student registration
    @PostMapping("/studentreg")
    public String consultantReg(@RequestParam String username, String name, String email, String faculty, String specialization, String password, Model model){
        String success = userService.studentReg(username, name, email, faculty, specialization, password);
        model.addAttribute("regSuccess", success);
        return (success == "true") ? "forward:login" : "studentreg";
    }

    //Consultant select Student for more information
    @GetMapping("/consultant/studentdetails")
    public String studentDetails(@RequestParam String topicid, Model model){
        Student student;
        try{
            student = userService.getStudentDetails(Integer.parseInt(topicid));
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:home";
        }

        Topic topic = topicService.getTopicById(Integer.parseInt(topicid));
        List<Step> steps = stepService.findAllStepsByTopicId(Integer.parseInt(topicid));
        List<Consultation> consultations = consultationRepository.findAllByTopicid(Integer.parseInt(topicid));
        List<Consultation> showConsultations = new LinkedList<>();
        for(Consultation c : consultations){
            if(c.getStatus().compareTo("Elfogadásra vár") == 0 || c.getStatus().compareTo("Elfogadva") == 0){
                showConsultations.add(c);
            }
        }
        model.addAttribute("consultations", showConsultations);
        model.addAttribute("student", student);
        int stepDone = 0;
        for(Step step : steps){
            if(step.getStepstatus()==1) {
                stepDone += step.getPercentage();
            }
        }
        model.addAttribute("donePercentage", stepDone);
        model.addAttribute("topic",topic);
        model.addAttribute("steps", steps);

        return "consultant/studentdetails";
    }
}
