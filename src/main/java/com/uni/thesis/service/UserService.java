package com.uni.thesis.service;


import com.uni.thesis.model.*;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.ConsultantRoleRepository;
import com.uni.thesis.repository.StudentRepository;
import com.uni.thesis.repository.StudentRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    ConsultantRoleRepository consultantRoleRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentRoleRepository studentRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TopicService topicService;


    public String consultantReg(String username, String name, String email, String password){

        if(consultantRepository.findConsultantByUsername(username).isPresent()){
            return "already";
        }else{
            Consultant consultant = new Consultant();
            consultant.setUsername(username);
            consultant.setName(name);
            consultant.setEmail(email);
            String secPassword = passwordEncoder.encode(password);
            consultant.setPassword(secPassword);
            try{
                consultant = consultantRepository.save(consultant);
            }catch (Exception e){
                e.printStackTrace();
            }

            ConsultantRole consultantRole = new ConsultantRole();
            consultantRole.setConsultant_id(consultant.getConsultantid());
            consultantRole.setRole_id(2);
            try{
                consultantRoleRepository.save(consultantRole);
                return "true";
            }catch (Exception e){
                e.printStackTrace();
                return "false";
            }
        }
    }


    public String studentReg(String username, String name, String email, String faculty, String specialization, String password){

        if(studentRepository.findStudentByUsername(username).isPresent()){
            return "already";
        }else{
            Student student = new Student();
            student.setUsername(username);
            student.setName(name);
            student.setEmail(email);
            student.setFaculty(faculty);
            student.setSpecialisation(specialization);
            String secPassword = passwordEncoder.encode(password);
            student.setPassword(secPassword);
            try{
                student = studentRepository.save(student);
            }catch (Exception e){
                e.printStackTrace();
            }

            StudentRole studentRole = new StudentRole();
            studentRole.setStudent_id(student.getStudentid());
            studentRole.setRole_id(3);
            try{
            studentRoleRepository.save(studentRole);
                return "true";
            }catch (Exception e){
                e.printStackTrace();
                return "false";
            }
        }
    }

    public Student getStudentDetails(int topicid){
        Topic topic = topicService.getTopicById(topicid);

        return studentRepository.findStudentByTopicid(topic).get();
    }

}
