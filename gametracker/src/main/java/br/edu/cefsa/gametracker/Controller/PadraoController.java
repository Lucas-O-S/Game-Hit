package br.edu.cefsa.gametracker.Controller;

import org.springframework.ui.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract  class PadraoController{
    protected String formLink = "/form";
    protected abstract String Form(Model model);

}
