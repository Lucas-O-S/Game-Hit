package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import br.edu.cefsa.gametracker.Model.JogoModel;
import jakarta.servlet.http.HttpSession;

public class JogoController extends PadraoController<JogoModel> {

    @Override
    protected String Cadastro(Model model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String Editar(Model model, Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean Validar(JogoModel model, char operacao, Model valores) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String Buscar(HttpSession session, Model valores, String valor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String Excluir(HttpSession session, long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
