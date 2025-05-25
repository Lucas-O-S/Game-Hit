package br.edu.cefsa.gametracker.Model;

import java.time.LocalDate;

import br.edu.cefsa.gametracker.Enum.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor
@AllArgsConstructor
public class RegistroModel extends PadraoModel {

    @Column(name = "tempoJogo")
    private Long tempoJogo;

    @Column(name = "dataFinalizacao")
    private LocalDate dataFinalizacao;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "comentarios")
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable=false)
    private JogoModel jogo;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable=false)
    private UsuarioModel usuario;

    @Column(name = "Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public String getTempoJogoFormatado() {
        if (this.tempoJogo == null) {
            return "0h 0m";
        }
        long horas = tempoJogo / 60;
        long minutos = tempoJogo % 60;
        return String.format("%dh %02dm", horas, minutos);
    }
}