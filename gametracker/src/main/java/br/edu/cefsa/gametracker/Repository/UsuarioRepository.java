package br.edu.cefsa.gametracker.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    

    public int countByAdm(boolean ADM);
    @Query("SELECT FUNCTION('MONTH', u.createdAt) AS mes, COUNT(u) AS qnt " +
    "FROM JogoModel u " +
    "WHERE u.createdAt >= :dataInicio " +
    "GROUP BY FUNCTION('MONTH', u.createdAt), FUNCTION('YEAR', u.createdAt) " +
    "ORDER BY FUNCTION('YEAR', u.createdAt), FUNCTION('MONTH', u.createdAt)")
    List<Object[]> usuariosUltimoAno(@Param("dataInicio") LocalDateTime dataInicio);

}
