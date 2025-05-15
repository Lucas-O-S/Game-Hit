package br.edu.cefsa.gametracker.Model;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;

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

        if (fotoByte != null) {
            this.fotoByte = fotoByte;
            this.fotoBase64 = java.util.Base64.getEncoder().encodeToString(fotoByte);
        } else {
            try {
                ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultUserImage.png");
                this.fotoByte = Files.readAllBytes(imgFile.getFile().toPath());
            } catch (IOException e) {
                this.fotoByte = null;
                this.fotoBase64 = null;
                e.printStackTrace();
            }
        }
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
