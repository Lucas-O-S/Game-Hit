package br.edu.cefsa.gametracker.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.cefsa.gametracker.Enum.Estado;
import br.edu.cefsa.gametracker.Model.RegistroModel;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroModel, Long> {

    @Query("SELECT r FROM RegistroModel r JOIN r.jogo j " +
        "WHERE r.usuario.id = :usuarioId " +
        "AND (:jogoNome IS NULL OR LOWER(j.nome) LIKE LOWER(CONCAT('%', :jogoNome, '%'))) " +
        "AND (:estado IS NULL OR r.estado = :estado) " +
        "AND (:minNota IS NULL OR r.nota >= :minNota)")
    Page<RegistroModel> findByFilters(@Param("usuarioId") Long usuarioId,
                                    @Param("jogoNome") String jogoNome,
                                    @Param("estado") Estado estado,
                                    @Param("minNota") Integer minNota,
                                    Pageable pageable);

    @Query("SELECT r FROM RegistroModel r JOIN r.jogo j WHERE LOWER(j.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<RegistroModel> findByJogoNomeContainingIgnoreCase(@Param("nome") String nome);
}


