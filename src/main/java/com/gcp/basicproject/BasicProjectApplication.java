package com.gcp.basicproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Admin
 */
@SpringBootApplication(scanBasePackages = "com.*.*")
public class BasicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicProjectApplication.class, args);
    }

}
