package br.edu.cefsa.gametracker.Controller;


import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/Usuario")
@Getter
@Setter
public class UsuarioController extends PadraoController <UsuarioModel> {

    @Autowired
    UsuarioService usuarioService;

    UsuarioController(){
        this.precisaLogar = false;
    }

    @Override
    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores){
        valores.addAttribute("usuario", new UsuarioModel());
        valores.addAttribute("operacao", 'I');
        return "Usuario/Cadastro";
    }

    
    @RequestMapping("/Login")
    public String Login(Model valores){
        valores.addAttribute("usuario", new UsuarioModel());
        return "Usuario/Login";
    }

    @PostMapping("/Salvar")
    public String Save(
        @ModelAttribute UsuarioModel model,
         @RequestParam("operacao") char operecao,
          Model valores,
          @RequestParam("imagem") MultipartFile imagem
          ) {
        try{
            if(Validar(model, operecao)){                    
                if (imagem != null && !imagem.isEmpty()) {
                    model.setFotoByte(imagem.getBytes());
                }

                if(operecao == 'I'){


                    usuarioService.Inserir(model);

                }
                else{
                    if(usuarioService.BuscarPorId(model.getId()) != null){
                        usuarioService.Editar(model);

                    }
                    else{
                        valores.addAttribute("usuario", model);
                        valores.addAttribute("operacao", operecao);
                        return "/Usuario/Cadastro";

                    }
                }
                return "redirect:/index";

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


    @GetMapping("/Autenticar")
    public String Autenticar(Model valores, UsuarioModel model, HttpSession session){
        try{
            if(usuarioService.Login(model.getEmail(), model.getSenha()) != null){
                session.setAttribute("usuario", model);
                
                return "redirect:/index";
            }
            else{
                valores.addAttribute("usuario", new UsuarioModel());

                return "/Usuario/Login";

            }
        }
        catch(Exception e){
            System.out.println("Erro ao salvar usuario: " + e.getMessage());
        }
        return "redirect:/index";

    }



    @GetMapping("/logout")
    public String logout(HttpSession session, Model valores) {
        session.invalidate(); 
        valores.addAttribute("usuario", new UsuarioModel());
        return "redirect:/Usuario/Login";
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
