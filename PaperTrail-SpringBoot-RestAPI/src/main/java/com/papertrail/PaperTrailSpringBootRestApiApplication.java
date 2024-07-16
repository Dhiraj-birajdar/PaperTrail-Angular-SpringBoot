package com.papertrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PaperTrailSpringBootRestApiApplication {

    public static void main(String[] args) {

		SpringApplication.run(PaperTrailSpringBootRestApiApplication.class, args);
    }
}
