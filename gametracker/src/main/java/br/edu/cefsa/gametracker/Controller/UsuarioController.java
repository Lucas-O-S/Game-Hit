package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.cefsa.gametracker.Repository.UsuarioRepository;

@Controller
public class UsuarioController extends PadraoController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected String Form() {
        return "/usuario/form";
    }
  

}
