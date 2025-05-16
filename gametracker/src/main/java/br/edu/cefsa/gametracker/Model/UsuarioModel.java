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

    //Construtor de model
    public UsuarioModel(String nome, String email, String senha, String telefone, Boolean adm, byte[] fotoByte) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.adm = adm;
        this.fotoByte = fotoByte;

        //Se não tiver foto usa uma default
        if (fotoByte != null) {
            this.fotoByte = fotoByte;

            //Preenche a foto em base 64 com a foto em byte 
            this.fotoBase64 = java.util.Base64.getEncoder().encodeToString(fotoByte);
        } else {
            try {

                //Busca a imagem generica e preenche foto byte para depois preencher em base 64
                ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultUserImage.png");
                this.fotoByte = Files.readAllBytes(imgFile.getFile().toPath());
                this.fotoBase64 = java.util.Base64.getEncoder().encodeToString(fotoByte);

                //Caso de erro
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

    //Transient que dizer que não ira para o BD
    @Transient
    String fotoBase64;
}
