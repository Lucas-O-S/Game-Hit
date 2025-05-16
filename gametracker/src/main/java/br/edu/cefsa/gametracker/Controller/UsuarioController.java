package br.edu.cefsa.gametracker.Controller;


import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    /*
     * Conceitos uteis
     * Tipo Model: devolve a pagina variaveis do java, util para transfeir dados
     * Tipo Session: Mantem dados salvos enquanto a sessão estiver rodando
     * Qualquer variavel que a pagina tenha nas sessões th é obrigatorio o envio, mesmo que seja vazio, se não tera erro ao carregar
     * @RequestParam("Nome da variavel") permite acessar dados de dentro da url
     */


    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //Manda para a pagina de formulario para cadastrar
    @Override
    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores){
        //Adiciona a model um usuario vazio e uma operação
        valores.addAttribute("usuario", new UsuarioModel());
        valores.addAttribute("operacao", 'I');
        valores.addAttribute("error", "");

        return "Usuario/Form";
    }

    //Manda para a pagina de Formulario para editar
    @Override
    @RequestMapping("/Editar")
    public String Editar(Model valores, @RequestParam("id") Long id){    

        //Devolve um usuario com base no id
        UsuarioModel usuario = usuarioService.BuscarPorId(id);

        //Devolve par index caso não tenha um usuario 
        if(usuario == null){
            return "redirect:/index";
        }
        valores.addAttribute("usuario", usuario);
        valores.addAttribute("operacao", 'E');

        return "Usuario/Form";
    }

    @RequestMapping("/Login")
    public String Login(
            Model valores, 
            @RequestParam(value = "error", required = false) String error
    ) {        
        if (error != null) {
            valores.addAttribute("erro", "Email ou senha inválidos");
        }

        return "Usuario/Login";
    }


    //Manda para a pagina de perfil
    @RequestMapping("/Perfil")
    public String Perfil(Model valores, @RequestParam("id") Long id){   

        UsuarioModel usuario = usuarioService.BuscarPorId(id);
        
        if(usuario == null){
            return "redirect:/index";
        }

        valores.addAttribute("usuario", usuario);
        return "Usuario/Perfil";
    }

    //Exclui o usuario
    @Override
    @PostMapping("/Excluir")
    protected String Excluir(HttpSession session, @RequestParam("id") long id){
        try {

            usuarioService.Excluir(id); 

            return "redirect:/index";
        } catch (Exception e) {
            System.out.println("Erro ao excluir usuario: " + e.getMessage());
            return "Home/Index";
        }
    }


    //Busca usuarios com base no nome
    @Override
    @GetMapping("/Buscar")
    public String Buscar(HttpSession session, Model valores, @RequestParam(name = "nomeBuscar", required = false) String nome){
        try{ 

            List<UsuarioModel> usuarios = new ArrayList<>();
            
            //Se o nome não for enviado sera devolvido uma lista vazia
            if(nome != null && !nome.isEmpty()){
            usuarios = usuarioService.BuscarPorNome(nome);

            }   
            
            valores.addAttribute("usuarios", usuarios);
            return "Usuario/Busca";
        }
        catch(Exception e){

            System.out.println("Erro ao salvar usuario: " + e.getMessage());
            return "Home/Index";

        }

    }


    //Salva um usuario, seja editar ou alterar
    @PostMapping("/Salvar")
    public String Save(
        @ModelAttribute UsuarioModel model,
        @RequestParam("operacao") char operecao,
        Model valores,
        @RequestParam("imagem") MultipartFile imagem
    ) {
        try{
            //Valida o usuario antes de seguir, caso não aceite sera devolvido um erro e retornara para a pagina de onde veio
            if(Validar(model, operecao, valores)){                    
                
                //Verifica se uma imagem foi enviada, caso não ira ser usado uma default
                if (imagem != null && !imagem.isEmpty()) {
                    model.setFotoByte(imagem.getBytes());
                }
                else{
                    ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultUserImage.png");
                    model.setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));  
                }
                
                //Se for uma inserção
                if(operecao == 'I'){
                    usuarioService.Inserir(model);
                }

                //Caso seja edição
                else{
                    usuarioService.Editar(model);
                }
                return "redirect:/index";

            }
            else{
                valores.addAttribute("usuario", model);
                valores.addAttribute("operacao", operecao);
                return "/Usuario/Form";
            }

        }
        catch(Exception e){

            System.out.println("Erro ao salvar usuario: " + e.getMessage());
        }
        return "redirect:/index";
    }


    //Autentica o usuario na tela de login
    @PostMapping("/Autenticar")
    public String Autenticar(Model valores, UsuarioModel model, HttpSession session){
        try{
            //Busca o usuario 
            UsuarioModel usuario = usuarioService.Login(model.getEmail(), model.getSenha());
            
            //Verifica se existe
            if(usuario != null){
                session.setAttribute("usuario", usuario);
                
                return "redirect:/index";
            }

            //Devolve um erro
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
    

    //Muda o status de adm
    @RequestMapping("/MudarAdm")
    public String MudarAdm(@RequestParam("id") long id, @RequestParam("status") boolean status){
        usuarioService.MudarAdm(status, id);
        return "redirect:/Usuario/Perfil?id=" + id;
    }

    //Valida o usuario
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


    public boolean checarSenha(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

  

}
