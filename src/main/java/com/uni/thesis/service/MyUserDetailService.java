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
public class ConsultantDetailService implements UserDetailsService {
    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override  //Felhasználó adatai Spring Securitynek
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Consultant consultant = null;
        Student student = null;
        if(consultantRepository.findConsultantByUsername(username).isPresent()){
            consultant = consultantRepository.findConsultantByUsername(username).get();
        }else if(studentRepository.findStudentByUsername(username).isPresent()){
            student = studentRepository.findStudentByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        }
        return (consultant==null)?new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(),
                getSAuthorities(student)):new org.springframework.security.core.userdetails.User(consultant.getUsername(), consultant.getPassword(),
                getCAuthorities(consultant));

    }

    //Jogok kikeresése
    private static Collection<? extends GrantedAuthority> getCAuthorities(Consultant user) {
        String[] userRoles = user.getConsultantRoles().stream().map((role) -> role.getName()).toArray(String[]::new); // egy tömbbe rakja a role-okat
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

    private static Collection<? extends GrantedAuthority> getSAuthorities(Student user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new); // egy tömbbe rakja a role-okat
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
