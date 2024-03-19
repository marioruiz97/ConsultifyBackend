package com.asisge.consultifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ConsultifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultifyBackendApplication.class, args);
	}

}
