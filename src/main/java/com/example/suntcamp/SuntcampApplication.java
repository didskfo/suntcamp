package com.example.suntcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuntcampApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuntcampApplication.class, args);
    }

}
