package br.edu.cefsa.gametracker.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor
public class JogoModel extends PadraoModel{
        public JogoModel(String nome, LocalDate dataLancamento, String sinopse, String criador) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.sinopse = sinopse;
        this.criador = criador;
    }

    @Column(name = "nome", nullable = false, unique=true)
    private String nome;

    @Column(name = "dataLancamento")
    private LocalDate dataLancamento;

    @Column(name = "sinopse")
    private String sinopse;

    @Column(name = "criador")
    private String criador;

    @Column(name = "foto", columnDefinition = "BLOB")
    private byte[] fotoByte;

    @Transient // não mapeia o campo como coluna no banco de dados
    private String fotoBase64;


}