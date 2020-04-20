package com.uni.thesis.service;

import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.model.Topic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class FileService {
    FileInputStream fileInputStream = null;
    private XWPFDocument document = null;
    XWPFWordExtractor extractor = null;
    private FileOutputStream fout = null;
    LocalDate date = LocalDate.now();



    Topic topic =null;
    Student student = null;
    Consultant consultant = null;

    @Autowired
    TopicService topicService;
    @Autowired
    FileService fileService;


    public int changeText(int topicid, String subject, String leader, String cdepartment, String thesisrelease, String thesisdeadline) {
        try {
            topic = topicService.getTopicById(topicid);
            student = topic.getStudent();
            consultant = topic.getConsultantid();
            fileInputStream = new FileInputStream("src/main/resources/static/files/minta.docx");
            document = new XWPFDocument(fileInputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    check(r, subject, leader, cdepartment, thesisrelease, thesisdeadline);
                }
            }
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            check(r, subject, leader, cdepartment, thesisrelease, thesisdeadline);
                        }
                    }
                }
            }
        }

        try {
            File newFilePath = new File("../"+student.getUsername()+".docx");
            fout = new FileOutputStream(newFilePath.toString());
            document.write(fout);
            fout.close();
            document.close();

        }catch(FileAlreadyExistsException fex){
            fex.printStackTrace();
            return 1;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return 2;
        }

        return 0;
    }

    public void check(XWPFRun r, String subject, String leader, String cdepartment, String thesisrelease, String thesisdeadline) {
        String text = r.getText(0);
        if (text != null) {
            switch (text) {
                case "NEVXXX":
                    text = text.replace("NEVXXX", student.getName());
                    break;
                case "SZAKXXX":
                    text = text.replace("SZAKXXX", student.getFaculty());
                    break;
                case "SZAKIRANYXXX":
                    text = text.replace("SZAKIRANYXXX", student.getSpecialisation());
                    break;
                case "NEPTUNXXX":
                    text = text.replace("NEPTUNXXX", student.getUsername());
                    break;
                case "EVSZAMXXX":
                    text = text.replace("EVSZAMXXX", Integer.toString(date.getYear()));
                    break;
                case "TARGYKORXXX":
                    text = text.replace("TARGYKORXXX", subject);
                    break;
                case "TEMACIMXXX":
                    text = text.replace("TEMACIMXXX", topic.getName());
                    break;
                case "FELADATLEIRASXXX":
                    text = text.replace("FELADATLEIRASXXX", topic.getDescription());
                    break;
                case "TERVEZESVEZETOXXX":
                    text = text.replace("TERVEZESVEZETOXXX", consultant.getName());
                    break;
                case "TERVVEZTANSZEKBEOSZTASXXX":
                    text = text.replace("TERVVEZTANSZEKBEOSZTASXXX", leader);
                    break;
                case "KONZULENSXXX":
                    text = text.replace("KONZULENSXXX", consultant.getName());
                    break;
                case "CEGESBEOSZTASXXX":
                    text = text.replace("CEGESBEOSZTASXXX", cdepartment);
                    break;
                case "KIADASDATUMXXX":
                    text = text.replace("KIADASDATUMXXX", thesisrelease);
                    break;
                case "BEADASDATUMXXX":
                    text = text.replace("BEADASDATUMXXX", thesisdeadline);
                    break;
                case "KELTXXX":
                    text = text.replace("KELTXXX", date.toString());
                    break;
            }

            r.setText(text, 0);
        }
    }

    public ResponseEntity<ByteArrayResource> download(String file){
        Path sourcepath = Paths.get("../"+file+".docx");
        try{
            byte[] data = Files.readAllBytes(sourcepath);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+sourcepath.getFileName().toString())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}







/*



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