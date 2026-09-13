package com.person;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.person")
public class PersonelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonelApplication.class, args);
        System.out.println("===========>START PERSONEL APPLICATION<===========");
    }

    @Bean
    public ModelMapper initMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
}
