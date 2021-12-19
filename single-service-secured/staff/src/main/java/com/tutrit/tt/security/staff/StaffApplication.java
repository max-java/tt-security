package com.tutrit.tt.security.staff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = "com.tutrit")
public class StaffApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaffApplication.class, args);
    }
}
