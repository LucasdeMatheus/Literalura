package br.com.alura.desafio.literalura;

import br.com.alura.desafio.literalura.repositorio.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.alura.desafio.literalura.principal.Principal;

@SpringBootApplication
public class LiteraluraApplication  implements CommandLineRunner {

    @Autowired
    private AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(autorRepositorio);
		principal.exibeMenu();
	}
}
