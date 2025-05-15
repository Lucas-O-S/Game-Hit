package br.edu.cefsa.gametracker.Config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UsuarioService usuarioService) {
        return args -> {
            // Verifica se o usuário já existe (exemplo: por email)
            if (usuarioService.VerificarEmail("SA@Email.com") == false) {
                UsuarioModel usuario = new UsuarioModel(
                    "SA", "SA@Email.com", "123", "", true, null);
                usuarioService.Inserir(usuario);
                System.out.println("Usuário inicial criado.");
            } else {
                System.out.println("Usuário inicial já existe.");
            }
        };
    }
}
