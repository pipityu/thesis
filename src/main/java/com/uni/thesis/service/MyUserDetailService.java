package com.uni.thesis.service;

import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    StudentRepository studentRepository;

    //Get the informations to the Spring Boot, it is NOT return all the datas which are stored in DB(it return a UserDetails OBJ)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Consultant consultant = null;
        Student student = null;

        if(consultantRepository.findConsultantByUsername(username).isPresent()){
            consultant = consultantRepository.findConsultantByUsername(username).get();
        }else if(studentRepository.findStudentByUsername(username).isPresent()){
            student = studentRepository.findStudentByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        }else{
            throw new UsernameNotFoundException("Nincs felhasználó ezzel a névvel: " + username);
        }
        return (consultant==null)?new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(),
                getSAuthorities(student)):new org.springframework.security.core.userdetails.User(consultant.getUsername(), consultant.getPassword(),
                getCAuthorities(consultant));

    }

    //Get rights to the Consultant
    private static Collection<? extends GrantedAuthority> getCAuthorities(Consultant user) {
        String[] userRoles = user.getConsultantRoles().stream().map((role) -> role.getName()).toArray(String[]::new); // egy tömbbe rakja a role-okat
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

    //Get rights to the Student
    private static Collection<? extends GrantedAuthority> getSAuthorities(Student user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new); // egy tömbbe rakja a role-okat
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

    //Get Consultant details which can be used in controllers(UserController login)
    public Consultant loadConsultant(String userName) throws UsernameNotFoundException{
        Consultant consultant = consultantRepository.findConsultantByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + userName + " not found"));
        return consultant;
    }

    //Get Student details which can be used in controllers(UserController login)
    public Student loadStudent(String userName) throws UsernameNotFoundException{
        Student student = studentRepository.findStudentByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email: " + userName + " not found"));
        return student;
    }
}
