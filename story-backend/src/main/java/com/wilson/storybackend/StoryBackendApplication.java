package com.wilson.storybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StoryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryBackendApplication.class, args);
    }

}
