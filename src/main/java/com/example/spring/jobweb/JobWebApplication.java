package com.example.spring.jobweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class} )
@SpringBootApplication
@EnableScheduling
public class JobWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobWebApplication.class, args);
	}
}