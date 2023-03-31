package com.example.trollgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TrollGgApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrollGgApplication.class, args);
    }
}
