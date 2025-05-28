package br.edu.cefsa.gametracker.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.cefsa.gametracker.service.JogoService;
import br.edu.cefsa.gametracker.service.RegistroService;
import br.edu.cefsa.gametracker.service.UsuarioService;

@Controller
@RequestMapping("ADM")
public class AdmController {
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JogoService jogoService;

    @Autowired
    RegistroService registroService;

    @RequestMapping("/Area")
    public String AdmArea(Model valores){
        try{
            valores.addAttribute("totalUsuarios", usuarioService.TotalUsuarios());
            valores.addAttribute("Usuarios", usuarioService.ListarTodos());
            valores.addAttribute("UsuarioAno",  usuarioService.UsuariosUltimoAno());

            valores.addAttribute("totalJogos", jogoService.TotalJogos());
            valores.addAttribute("Jogos", jogoService.ListarTodos());



            valores.addAttribute("totalRegistros", registroService.TotalRegistros());
            valores.addAttribute("Registros", registroService.TotalRegistros());



            return "ADM/AreaAdm";
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:index";
        }


    }
}
