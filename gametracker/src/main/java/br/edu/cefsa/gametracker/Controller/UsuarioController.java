package br.edu.cefsa.gametracker.Controller;


import java.util.ArrayList;
import java.util.List;

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

    @Override
    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores){
        valores.addAttribute("usuario", new UsuarioModel());
        valores.addAttribute("operacao", 'I');

        return "Usuario/Cadastro";
    }

    @Override
    @RequestMapping("/Editar")
    public String Editar(Model valores, HttpSession session){
        if(!VerificarLogin(session)){
            return "redirect:/Usuario/Login";
        }        
        valores.addAttribute("usuario", session.getAttribute("usuario"));
        valores.addAttribute("operacao", 'E');

        return "Usuario/Cadastro";
    }

    
    @RequestMapping("/Login")
    public String Login(Model valores){
        valores.addAttribute("usuario", new UsuarioModel());
        return "Usuario/Login";
    }

    @RequestMapping("/Perfil")
    public String Perfil(Model valores, HttpSession session){
        if(!VerificarLogin( session)){
            return "redirect:/Usuario/Login";
        }        
        valores.addAttribute("usuario", session.getAttribute("usuario"));
        return "Usuario/Perfil";
    }

    @RequestMapping("/Excluir")
    public String Excluir(HttpSession session){
        if(!VerificarLogin(session)){
            return "redirect:/Usuario/Login";
        }       
        var usuario = (UsuarioModel) session.getAttribute("usuario");
        usuarioService.Excluir(usuario.getId()); 
        session.invalidate(); 
        return "redirect:/Usuario/Login";
    }

    @RequestMapping("/BuscarPerfil")
    public String BuscarPerfil(HttpSession session, Model valores){
        if(!VerificarLogin(session)){
            return "redirect:/Usuario/Login";
        }        
        List<UsuarioModel> usuarios = new ArrayList<UsuarioModel>();
        valores.addAttribute("usuarios", usuarios);

        return "Usuario/Busca";
    }

    @GetMapping("/Buscar")
    public String Buscar(HttpSession session, Model valores, @RequestParam("nomeBuscar") String nome){
        if(!VerificarLogin(session)){
            return "redirect:/Usuario/Login";
        }        
        List<UsuarioModel> usuarios = usuarioService.BuscarPorNome(nome);
        valores.addAttribute("usuarios", usuarios);
        return "Usuario/Busca";
    }

    @PostMapping("/Salvar")
    public String Save(
        @ModelAttribute UsuarioModel model,
         @RequestParam("operacao") char operecao,
          Model valores,
          @RequestParam("imagem") MultipartFile imagem,
        HttpSession session
        ) {
        try{
            if(Validar(model, operecao, valores)){                    
                if (imagem != null && !imagem.isEmpty()) {
                    model.setFotoByte(imagem.getBytes());
                }

                if(operecao == 'I'){


                    usuarioService.Inserir(model);

                }
                else{
                    if(usuarioService.BuscarPorId(model.getId()) != null){
                        usuarioService.Editar(model);
                        model.setFotoBase64(java.util.Base64.getEncoder().encodeToString(model.getFotoByte()));
                        session.setAttribute("usuario", model);
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
            UsuarioModel usuario = usuarioService.Login(model.getEmail(), model.getSenha());
            if(usuario != null){
                usuario.setFotoBase64(java.util.Base64.getEncoder().encodeToString(usuario.getFotoByte()));
                session.setAttribute("usuario", usuario);
                
                return "redirect:/index";
            }
            else{
                valores.addAttribute("usuario", new UsuarioModel());
                valores.addAttribute("erro", "Email ou senha inválidos");
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
    protected boolean Validar(UsuarioModel model, char operacao, Model valores) {
        if(model.getNome() == null || model.getNome().isEmpty() || model.getNome().length() > 250){
            valores.addAttribute("erro", "Erro ao validar Nome");

            return false;
        }
        if(model.getEmail() == null || model.getEmail().isEmpty() || model.getEmail().length() > 250){
        valores.addAttribute("erro", "Erro ao validar Email");

            return false;
        }
        if (model.getEmail().contains("@") == false || model.getEmail().contains(".") == false){
            valores.addAttribute("erro", "Email não é válido");

            return false;
            
        }

        if (usuarioService.VerificarEmail(model.getEmail()) && operacao == 'I'){
            valores.addAttribute("erro", "Email já cadastrado");

            return false;
        }

        if(model.getSenha() == null || model.getSenha().isEmpty() || model.getSenha().length() > 250){
            valores.addAttribute("erro", "Erro ao validar senha");

            return false;
        }


        if(model.getTelefone() == null || model.getTelefone().isEmpty() || model.getTelefone().length() > 250){
            valores.addAttribute("erro", "Erro ao validar Telefone");

            return false;
        }
        return true;
    }




  

}
