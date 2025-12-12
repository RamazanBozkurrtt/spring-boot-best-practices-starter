package org.project.bestpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BestPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestPracticeApplication.class, args);
	}


}
