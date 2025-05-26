package com.alibou.security;

import com.alibou.security.classi.generale.Generale;
import com.alibou.security.classi.generale.GeneraleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication implements CommandLineRunner {
	@Autowired
	private GeneraleRepository generaleRepository;


	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}


	@Override
	@Transactional
	public void run(String... args) {
		String versione = "1.0.0";

		Generale generale = generaleRepository.findById(1).orElse(new Generale());
		generale.setId(1);
		generale.setVersione(versione);
		generaleRepository.save(generale);
	}
}