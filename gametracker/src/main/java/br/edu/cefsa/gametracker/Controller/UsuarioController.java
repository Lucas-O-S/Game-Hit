package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends PadraoController {

    @Autowired
    UsuarioService usuarioService;

    @Override
    @RequestMapping("/Form")
    public String Form() {
        return "/usuario/form";
    }

    @PutMapping("/Salvar")
    public String Save(@ModelAttribute UsuarioModel model, char operecao) {
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
