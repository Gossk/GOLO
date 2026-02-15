package com.golo.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoloBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoloBackendApplication.class, args);
    }
}