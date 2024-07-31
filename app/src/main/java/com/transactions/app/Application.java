package com.transactions.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.transactions.app", "com.transactions.domain"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}