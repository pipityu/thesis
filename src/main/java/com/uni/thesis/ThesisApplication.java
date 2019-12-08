package com.uni.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class ThesisApplication {

    public static void main(String[] args) {

        ApplicationContext ct = SpringApplication.run(ThesisApplication.class, args);

        /*String [] arr = ct.getBeanDefinitionNames();

        Arrays.sort(arr);

        for(String name : arr){
            System.out.println(name);
        }*/
    }

}
