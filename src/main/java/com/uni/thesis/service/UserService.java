package com.uni.thesis.service;


import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.ConsultantRole;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.ConsultantRoleRepository;
import com.uni.thesis.repository.StudentRepository;
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
    PasswordEncoder passwordEncoder;


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

}
