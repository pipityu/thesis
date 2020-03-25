package com.uni.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class ThesisApplication {

    public static void main(String[] args) {

        ApplicationContext ct = SpringApplication.run(ThesisApplication.class, args);

        //Container with all the SpringBeans
        /*String [] arr = ct.getBeanDefinitionNames();
        Arrays.sort(arr);
        for(String name : arr){
            System.out.println(name);
        }*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
