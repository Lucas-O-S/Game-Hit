package br.edu.cefsa.gametracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.cefsa.gametracker.Enum.Estado;
import br.edu.cefsa.gametracker.Enum.Genero;
import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.Model.RegistroModel;
import br.edu.cefsa.gametracker.service.JogoService;
import br.edu.cefsa.gametracker.service.RegistroService;
import br.edu.cefsa.gametracker.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Registro")
public class RegistroController extends PadraoController<RegistroModel> {

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
    public String Editar(Model model, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Editar'");
    }

    @Override
    public boolean Validar(RegistroModel model, char operacao, Model valores) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Validar'");
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

    @Override
    protected String Cadastro(Model valores) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Cadastro'");
    }

    // Métodos específicos para o controlador de RegistroModel podem ser adicionados aqui

}
