package br.edu.cefsa.gametracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.cefsa.gametracker.Model.UsuarioModel;

//Interface que estende JpaRepository para operações CRUD
@Repository
public interface  UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    
    //Verifica se o email existe
    public Boolean existsByEmail(String email);

    public UsuarioModel findByEmail(String email);
    
    //Devolve uma lista com todos os usuarios que contenha a string enviada e ignora se esta maiusculo ou minusculo
    public List<UsuarioModel> findByNomeContainingIgnoreCase(String nome);
    
}
