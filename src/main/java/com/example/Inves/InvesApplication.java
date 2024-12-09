package com.example.Inves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class InvesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvesApplication.class, args);
	}

	@GetMapping
	public String hello(){
		return "Hello World";
	}
}
