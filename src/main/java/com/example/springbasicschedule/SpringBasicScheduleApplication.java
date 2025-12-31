package com.example.springbasicschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBasicScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicScheduleApplication.class, args);
    }

}
