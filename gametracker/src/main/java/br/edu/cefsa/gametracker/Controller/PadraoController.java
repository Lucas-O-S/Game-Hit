package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PadraoController<T>{

    protected abstract String Cadastro(Model model);

    protected abstract String Editar(Model model, HttpSession session, Long id);

    protected abstract boolean Validar(T model, char operacao, Model valores);

    protected abstract String Buscar(HttpSession session, Model valores, String valor);

    protected abstract String Excluir(HttpSession session, long id);

    protected boolean Perfil(HttpSession session){
        return session.getAttribute("usuario") != null;
    }
        
    protected Boolean VerificarLogin(HttpSession session){

        return !(session.getAttribute("usuario") == null);
    }



}
