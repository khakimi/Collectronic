package com.example.collectronic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollectronicApplication {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");

    public static void main(String[] args) {
        SpringApplication.run(CollectronicApplication.class, args);
    }

}
