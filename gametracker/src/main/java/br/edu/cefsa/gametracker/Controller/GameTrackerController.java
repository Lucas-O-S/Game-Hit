package br.edu.cefsa.gametracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gametracker")
public class GameTrackerController {
        @GetMapping("")
    public String index(){
        return "/index";
    }
}
