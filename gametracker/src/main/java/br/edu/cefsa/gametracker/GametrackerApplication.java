package br.edu.cefsa.gametracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class GametrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GametrackerApplication.class, args);
	}
}
