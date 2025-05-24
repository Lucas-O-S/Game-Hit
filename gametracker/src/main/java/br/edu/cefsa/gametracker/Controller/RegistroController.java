package br.edu.cefsa.gametracker.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.edu.cefsa.gametracker.Enum.Estado;
import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.Model.RegistroModel;
import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.JogoService;
import br.edu.cefsa.gametracker.service.RegistroService;
import br.edu.cefsa.gametracker.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Registro")
public class RegistroController extends PadraoAssociativaController<RegistroModel, JogoModel, UsuarioModel> {

    @Autowired
    RegistroService registroService;

    @Autowired
    JogoService jogoService;

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores, @RequestParam("jogoId") Long jogoId) {
        valores.addAttribute("jogo", jogoService.BuscarPorId(jogoId));
        valores.addAttribute("operacao", 'I');
        valores.addAttribute("erro", "");
        valores.addAttribute("registro", new RegistroModel());
        valores.addAttribute("estados", Estado.values());
        return "Registro/Form";

    }

    @Override
    public String Editar(Model valores,  @RequestParam("id")  Long id,  @RequestParam("id")  long jogoId) {
        valores.addAttribute("jogo", jogoService.BuscarPorId(jogoId));
        valores.addAttribute("operacao", 'E');
        valores.addAttribute("erro", "");
        valores.addAttribute("registro", new RegistroModel());
        valores.addAttribute("estados", Estado.values());
        
        return "Registro/Form";

    }

    @Override
    public boolean Validar(RegistroModel model, char operacao, Model valores) {
        if(model.getDataFinalizacao() == null){
            model.setDataFinalizacao( LocalDate.of(1753, 1, 1));
        }
        return true;
        
    }

    @Override
    public String Buscar(Model valores, String valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Buscar'");
    }

    @Override
    public String Excluir(HttpSession session, long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Excluir'");
    }

    @PostMapping("/Save")
    public String Save(
        HttpSession session,
        RegistroModel model,
        Model valores,
        @RequestParam("operacao") char operacao
    ){
        try{
            if (Validar(model, model.getId() == null ? 'I' : 'E', valores)) {
                if(operacao == 'I'){
                    registroService.Inserir(model);

                }
                else{
                    registroService.Editar(model);
                }
                return("redirect:/Usuario/Perfil?id=" + model.getUsuario().getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }





}
