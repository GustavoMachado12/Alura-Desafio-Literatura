package com.alura.literatura;

import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.LivroRepository;
import com.alura.literatura.service.ServiceLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired
	ServiceLivro serviceLivro;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Principal principal = new Principal(serviceLivro);
		principal.mostraMenu();
	}

}
