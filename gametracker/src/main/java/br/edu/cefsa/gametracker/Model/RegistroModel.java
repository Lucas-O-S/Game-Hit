package br.edu.cefsa.gametracker.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor

public class RegistroModel extends PadraoModel {
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
    @JoinColumn(name = "jogo_id")
    private JogoModel Jogo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel Usuario;
}