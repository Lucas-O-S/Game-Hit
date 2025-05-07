package br.edu.cefsa.gametracker.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass // Inidica que é uma classe pai que não sera mapeada
@Getter //Gera Getter e setter para os atributos da classe
@Setter
public abstract class PadraoModel {

    @Id
    @Column(name = "id")
    private Long id;
}
