package com.game.snakeandadot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class SnakeAndADotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnakeAndADotApplication.class, args);
	}
	
	@RequestMapping("/")
    String home() {
        return "Hello World!";
    }
}
