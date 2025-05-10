package br.edu.cefsa.gametracker.Model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor

public class RegistroModel {
    public RegistroModel(LocalDate tempoJogo, LocalDate dataFinalizacao, Integer nota, String comentarios) {
        this.tempoJogo = tempoJogo;
        this.dataFinalizacao = dataFinalizacao;
        this.nota = nota;
        this.comentarios = comentarios;
    }

    @Column(name = "tempoJogo")
    private LocalDate tempoJogo;

    @Column(name = "dataFinalizacao")
    private LocalDate dataFinalizacao;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "comentarios")
    private String comentarios;

    @ManyToOne
    private JogoModel Jogo;

    @ManyToOne
    private UsuarioModel Usuario;
}