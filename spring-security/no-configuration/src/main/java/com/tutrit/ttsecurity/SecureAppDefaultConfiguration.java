package com.tutrit.ttsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.authentication.AuthenticationFilter;

@SpringBootApplication
public class SecureAppDefaultConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(SecureAppDefaultConfiguration.class, args);
	}
}
