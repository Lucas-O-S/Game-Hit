package br.edu.cefsa.gametracker.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.service.JogoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/jogo")
public class JogoController extends PadraoController<JogoModel> {

    @Autowired
    JogoService jogoService;

    @Override
    @RequestMapping("/Cadastro")
    protected String Cadastro(Model model) {
        try {
            return "/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:home/index";

    }

    @Override
    @RequestMapping("/Editar")
    protected String Editar(Model model, Long id) {
        try {
            return "/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:home/index";    }

    @Override
    protected boolean Validar(JogoModel model, char operacao, Model valores) {
        if(model.getNome() == null || model.getNome().isEmpty()) {
            valores.addAttribute("erro", "O nome do jogo não pode ser vazio");
            return false;
        }
        if (jogoService.existeNome(model.getNome()) && operacao == 'I') {
            valores.addAttribute("erro", "Já existe um jogo cadastrado com esse nome");
            return false;
        }

        
        
        return true;
    }

    @Override
    @PostMapping("/Excluir")
    protected String Excluir(HttpSession session, long id) {
        try {

            jogoService.Excluir(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/home/index";
    }

    @Override
    @RequestMapping("/Buscar")
    protected String Buscar(HttpSession session, Model valores, String valor) {
        try {
            List<JogoModel> lista = jogoService.ListarTodos();
            valores.addAttribute("jogos", lista);
            return "/";
        }
         catch (Exception e) {
            e.printStackTrace();

        }
        return "/";
    }
    

    @PostMapping("/Salvar")
    public String Salvar(HttpSession session, JogoModel model, Model valores) {
        try {
            if (Validar(model, model.getId() == null ? 'I' : 'E', valores)) {
                if (model.getId() == null) {
                    jogoService.Inserir(model);
                } else {
                    jogoService.Editar(model);
                }
                return "redirect:/home/index";
                
            }
            else{
                valores.addAttribute("jogo", model);
                return "/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/home/index";
    }

}
