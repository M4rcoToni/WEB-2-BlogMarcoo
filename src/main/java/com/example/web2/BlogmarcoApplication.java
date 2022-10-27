package com.example.web2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// @SpringBootApplication
public class BlogmarcoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogmarcoApplication.class, args);
	}

}
