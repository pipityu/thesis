package com.uni.thesis.service;

import com.uni.thesis.model.Consultant;
import com.uni.thesis.model.Student;
import com.uni.thesis.repository.ConsultantRepository;
import com.uni.thesis.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

    @Override  //Felhasználó adatai Spring Securitynek
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Ez betölt egy user-t aminek a role-jából tudjuk, hogy milyen userről van szó. Ez alapján tudjuk a lekéréseket megoldani majd
        Consultant user = consultantRepository.findConsultantByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthorities(user));
    }

    //Jogok kikeresése
    private static Collection<? extends GrantedAuthority> getAuthorities(Consultant user) {
        String[] userRoles = user.getConsultantRoles().stream().map((role) -> role.getName()).toArray(String[]::new); // egy tömbbe rakja a role-okat
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
