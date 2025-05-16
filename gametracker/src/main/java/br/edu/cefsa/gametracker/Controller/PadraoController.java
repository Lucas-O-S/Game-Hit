package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PadraoController<T>{

    //Função abstrata para a tela de cadastro
    protected abstract String Cadastro(Model model);

    //Função abstrata para a tela de editar
    protected abstract String Editar(Model model, Long id);

    //Função abstrata para validar model
    protected abstract boolean Validar(T model, char operacao, Model valores);


    //Função abstrata para a tela de busca
    protected abstract String Buscar(HttpSession session, Model valores, String valor);

    //Função abstrata para a tela de exclusão 
    protected abstract String Excluir(HttpSession session, long id);
        
    //Verifica se há um login
    protected Boolean VerificarLogin(HttpSession session){

        return !(session.getAttribute("usuario") == null);
    }



}
