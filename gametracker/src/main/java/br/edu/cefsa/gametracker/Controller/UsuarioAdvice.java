package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;


//Essa classe roda em todas as controllers
@ControllerAdvice
public class UsuarioAdvice {

    @Autowired
    UsuarioService usuarioService;
    
    //Este metodo manda o usuario logado para todas as views, caso tenha um usuario logado
    @ModelAttribute("usuarioLogado")
    public UsuarioModel adicionarUsuarioLogado(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return usuarioService.BuscarEmail(userDetails.getUsername());
        }
        return null;
    }


}


