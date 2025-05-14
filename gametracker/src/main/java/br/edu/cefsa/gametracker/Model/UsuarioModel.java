package br.edu.cefsa.gametracker.Model;

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
    
    public UsuarioModel(String nome, String email, String senha, String telefone, Boolean adm, byte[] fotoByte) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.adm = adm;
        this.fotoByte = fotoByte;
        fotoBase64 = java.util.Base64.getEncoder().encodeToString(fotoByte);
    }

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "foto", columnDefinition = "BLOB")
    private byte[] fotoByte;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "adm", nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean adm = false;

    @Transient
    String fotoBase64;
}
