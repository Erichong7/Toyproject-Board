package com.example.ToyProject_Board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ToyProjectBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyProjectBoardApplication.class, args);
	}

}
