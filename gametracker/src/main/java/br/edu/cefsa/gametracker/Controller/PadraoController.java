package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract  class PadraoController<T>{
    protected String formLink = "/form";
    protected abstract String Cadastro(Model model);

    protected abstract boolean Validar(T model, char operacao);


}
