package com.uni.thesis.controller;


import com.uni.thesis.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/newfile")
    public String newFile(@RequestParam String topicid, String subject, String leader, String cdepartment, String thesisrelease, String thesisdeadline, RedirectAttributes redirectAttributes, Model model){
        int success = fileService.changeText(Integer.parseInt(topicid), subject, leader, cdepartment, thesisrelease, thesisdeadline);
        redirectAttributes.addAttribute("topicid", topicid);
        redirectAttributes.addFlashAttribute("success", success);

    return "redirect:consultant/studentdetails";
}

    @GetMapping(value = "/download", produces = "application/octet-stream")
    public ResponseEntity<ByteArrayResource> download(@RequestParam String file){
        return fileService.download(file);
    }

}













