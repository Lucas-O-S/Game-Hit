package br.edu.cefsa.gametracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.cefsa.gametracker.Model.UsuarioModel;

//Interface que estende JpaRepository para operações CRUD
@Repository
public interface  UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    public Boolean existsByEmail(String email);
    public UsuarioModel findByEmailAndSenha(String email, String Senha);
    List<UsuarioModel> findByNomeContainingIgnoreCase(String nome);

}
