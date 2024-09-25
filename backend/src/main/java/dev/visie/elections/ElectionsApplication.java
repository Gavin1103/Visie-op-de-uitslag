package dev.visie.elections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ElectionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectionsApplication.class, args);
	}
}
