/*
package com.uni.thesis.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



//-------------------------FILE FELTOLTESE KIJELOLT HELYRE------------------------------------------
@Controller
public class FileController {
    private static String FOLDER = "C:\\Users\\pipit\\IdeaProjects\\thesis\\src\\main\\resources\\files\\"; //RELATIV UTVONAL KELL!

    @PostMapping("/stepupload")
    private String singleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes){
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("nullFile", "Nem választottál ki fájlt!");
            return "redirect:student/home";
        }
        try{
            byte[] fileBytes = file.getBytes();
            Path path = Paths.get(FOLDER+file.getOriginalFilename());
            Files.write(path,fileBytes);
            redirectAttributes.addFlashAttribute("message", "Sikeresen feltöltötted a fájlt: '" + file.getOriginalFilename() + "'");

        }catch(IOException io){
            io.printStackTrace();
        }
        return "redirect:student/home";
    }
}
*/
