package org.project.bestpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.project.bestpractice.entities")
@EnableJpaRepositories(basePackages = "org.project.bestpractice.repository")
public class BestPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BestPracticeApplication.class, args);
	}

}
