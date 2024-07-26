package com.spring.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:/config/*.xml")
@ComponentScan(basePackages = "com.spring.coffee")
public class CoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplication.class, args);
	}

}
