package br.edu.cefsa.gametracker.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
public class UsuarioModel extends PadraoModel{
    

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;
}
