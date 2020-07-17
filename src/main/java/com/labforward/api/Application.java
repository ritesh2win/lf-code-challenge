package com.labforward.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
@ComponentScan("com.labforward")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
