package br.edu.cefsa.gametracker.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class UsuarioAdvice {
    @ModelAttribute("usuarioLogado")
    public UsuarioModel getUsuarioLogado(HttpSession session) {
        return (UsuarioModel) session.getAttribute("usuario");
    }
}


