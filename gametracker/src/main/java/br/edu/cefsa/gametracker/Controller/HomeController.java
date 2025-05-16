package br.edu.cefsa.gametracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
    
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
