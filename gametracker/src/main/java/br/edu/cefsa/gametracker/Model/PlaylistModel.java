package br.edu.cefsa.gametracker.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor

public class PlaylistModel {
        public PlaylistModel(String nome) {
        this.nome = nome;
    }

    @Column(name = "nome")
    private String nome;

    private List<RegistroModel> Playlist;
}