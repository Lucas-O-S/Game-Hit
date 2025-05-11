package br.edu.cefsa.gametracker.Model;

import java.io.FileInputStream;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity // mapeia a classe como uma entidade do banco de dados
@Getter
@Setter
@lombok.NoArgsConstructor
public class UsuarioModel extends PadraoModel{
    
    public UsuarioModel(String nome, String email, String senha, String telefone, Boolean adm) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.adm = adm;
    }

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "foto")
    private byte[] fotoByte;

    @Column(name = "telefone")
    private String telefone;

    @Transient // não mapeia o campo como coluna no banco de dados
    private FileInputStream foto;

    @Column(name = "adm", nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean adm = false;
}
