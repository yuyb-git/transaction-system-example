package com.example.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.example.transaction")
public class TransactionSystemExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionSystemExampleApplication.class, args);
    }

}
