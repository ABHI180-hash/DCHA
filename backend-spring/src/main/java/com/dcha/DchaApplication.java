package com.dcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DchaApplication.class, args);
        System.out.println("=================================================");
        System.out.println("  DCHA Java Spring Boot Backend Started Successfully! ");
        System.out.println("  Listening on http://localhost:8080               ");
        System.out.println("=================================================");
    }
}
