package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController extends PadraoController {

    @Autowired
    UsuarioService usuarioService;

    @Override
    @GetMapping("/Form")
    public String Form(Model model){
        model.addAttribute("usuario", new UsuarioModel());
        return "Usuario/Cadastro";
    }

    @PostMapping("/Salvar")
    public String Save(@ModelAttribute UsuarioModel model, @RequestParam("Operacao") char operecao) {
        try{
            if(operecao == 'I'){
                usuarioService.Inserir(model);

            }
            else{
                usuarioService.Editar(model);
            }
        }
        catch(Exception e){
            System.out.println("Erro ao salvar usuario: " + e.getMessage());
        }
        return "/usuario/form";
    }
    
  

}
