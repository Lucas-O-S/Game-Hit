package br.edu.cefsa.gametracker.Model;
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
public class PlaylistModel extends PadraoModel {
        public PlaylistModel(String nome) {
        this.nome = nome;
    }

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel Usuario;


    @ManyToOne
    @JoinColumn(name = "registro_id")
    private RegistroModel Playlist;
}