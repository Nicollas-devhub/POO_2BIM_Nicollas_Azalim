package br.edu.avaliacao.sacm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import br.edu.avaliacao.sacm.console.SistemaConsole;

@SpringBootApplication
public class SacmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SacmApplication.class, args);
	}

	  @Bean
    public CommandLineRunner iniciarSistema(SistemaConsole sistemaConsole) {
        return args -> sistemaConsole.executar();
    }

}
