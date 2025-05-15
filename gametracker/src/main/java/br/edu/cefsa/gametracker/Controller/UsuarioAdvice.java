package br.edu.cefsa.gametracker.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import jakarta.servlet.http.HttpSession;


//Essa classe roda em todas as controllers
@ControllerAdvice
public class UsuarioAdvice {

    //Este metodo manda o usuario logado para todas as views, caso tenha um usuario logado
    @ModelAttribute("usuarioLogado")
    public UsuarioModel getUsuarioLogado(HttpSession session) {
        return (UsuarioModel) session.getAttribute("usuario");
    }


}


