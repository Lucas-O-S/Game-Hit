package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.cefsa.gametracker.service.UsuarioService;

@Controller
@RequestMapping("")
public class HomeController {
    
    @Autowired
    UsuarioService usuarioService;

    //Manda para a index da home
    @RequestMapping("/index")
    public String index(){
        
        return "home/index";
    }

    //Se não tiver nada na url manda para a index da home
    @RequestMapping("")
    public String RedirectToIndex(){
        return "redirect:/index";
    }
}
