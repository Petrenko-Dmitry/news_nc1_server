package com.example.newsnc1server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsNc1Application {

    public static void main(String[] args) {
        SpringApplication.run(NewsNc1Application.class, args);
    }
}
