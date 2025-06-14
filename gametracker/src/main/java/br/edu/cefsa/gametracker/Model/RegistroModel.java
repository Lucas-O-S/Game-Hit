package br.edu.cefsa.gametracker.Model;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private JogoModel jogo;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsuarioModel usuario;

    @Column(name = "Estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public String getTempoJogoFormatado() {
        if (this.tempoJogo == null) {
            return "0h 0m 0s";
        }
        long horas = tempoJogo / 3600;
        long minutos = (tempoJogo % 3600) / 60;
        long segundos = tempoJogo % 60;
        return String.format("%dh %02dm %02ds", horas, minutos, segundos);
    }
}