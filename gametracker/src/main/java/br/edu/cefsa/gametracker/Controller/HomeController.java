package br.edu.cefsa.gametracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
    
    @RequestMapping("/index")
    public String index(){
        return "home/index";
    }

    @RequestMapping("")
    public String RedirectToIndex(){
        return "redirect:/index";
    }
}
