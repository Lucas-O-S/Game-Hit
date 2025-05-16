package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;


//Essa classe roda em todas as controllers
@ControllerAdvice
public class UsuarioAdvice {

    @Autowired
    UsuarioService usuarioService;
    
    //Este metodo manda o usuario logado para todas as views, caso tenha um usuario logado
    @ModelAttribute("usuarioLogado")
    public UsuarioModel adicionarUsuarioLogado(@AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
        if (userDetails != null) {
            UsuarioModel usuarioModel = usuarioService.BuscarEmail(userDetails.getUsername());
            session.setAttribute("usuarioLogado", usuarioModel);
            return usuarioModel;
                
        }
        return null;
    }


}


