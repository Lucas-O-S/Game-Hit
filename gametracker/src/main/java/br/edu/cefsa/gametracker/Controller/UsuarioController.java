package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController extends PadraoController <UsuarioModel> {

    @Autowired
    UsuarioService usuarioService;

    @Override
    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores){
        valores.addAttribute("usuario", new UsuarioModel());
        valores.addAttribute("operacao", 'I');
        return "Usuario/Cadastro";
    }

    @PostMapping("/Salvar")
    public String Save(@ModelAttribute UsuarioModel model, @RequestParam("operacao") char operecao, Model valores) {
        try{
            if(Validar(model, operecao)){
                if(operecao == 'I'){

                    System.out.println("Operacao: " + operecao);
                    System.out.println("Usuario: " + model.getNome());
                    System.out.println("Email: " + model.getEmail());
                    System.out.println("Senha: " + model.getSenha());
                    System.out.println("Telefone: " + model.getTelefone());
                    System.out.println("Administrador: " + model.getAdm());

                    usuarioService.Inserir(model);

                    return "redirect:/index";

                }
                else{

                    usuarioService.Editar(model);
                }
            }
            else{
                valores.addAttribute("usuario", model);
                valores.addAttribute("operacao", operecao);
                return "/Usuario/Cadastro";
            }

        }
        catch(Exception e){
            System.out.println("Erro ao salvar usuario: " + e.getMessage());
        }
        return "redirect:/index";
    }


    @Override
    protected boolean Validar(UsuarioModel model, char operacao) {
        if(model.getNome() == null || model.getNome().isEmpty() || model.getNome().length() > 250){
            return false;
        }
        if(model.getEmail() == null || model.getEmail().isEmpty() || model.getEmail().length() > 250){
            return false;
        }
        if (model.getEmail().contains("@") == false || model.getEmail().contains(".") == false){
            return false;
            
        }

        if (usuarioService.VerificarEmail(model.getEmail()) && operacao == 'I'){
            return false;
        }

        if(model.getSenha() == null || model.getSenha().isEmpty() || model.getSenha().length() > 250){
            return false;
        }


        if(model.getTelefone() == null || model.getTelefone().isEmpty() || model.getTelefone().length() > 250){
            return false;
        }
        return true;
    }




  

}
