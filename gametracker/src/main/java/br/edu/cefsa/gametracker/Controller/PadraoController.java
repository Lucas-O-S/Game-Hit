package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PadraoController<T>{
    
    protected Boolean VerificarLogin(Model model, HttpSession session){

        return !(session.getAttribute("usuario") == null);
    }
    protected abstract String Cadastro(Model model);
    protected abstract String Editar(Model model, HttpSession session);

    protected abstract boolean Validar(T model, char operacao, Model valores);

    protected boolean Perfil(HttpSession session){
        return session.getAttribute("usuario") != null;
    }



}
