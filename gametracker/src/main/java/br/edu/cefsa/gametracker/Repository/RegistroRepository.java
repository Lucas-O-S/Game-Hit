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

    @Query("SELECT COUNT(r) FROM RegistroModel r WHERE r.usuario.id = :usuarioId " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano)")
    Long countByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT r.estado, COUNT(r) FROM RegistroModel r " +
           "WHERE r.usuario.id = :usuarioId " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano) " +
           "GROUP BY r.estado")
    List<Object[]> countByEstadoGrouped(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT SUM(r.tempoJogo) FROM RegistroModel r WHERE r.usuario.id = :usuarioId " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano)")
    Long sumTempoJogoByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT AVG(r.nota) FROM RegistroModel r WHERE r.usuario.id = :usuarioId " +
           "AND r.nota IS NOT NULL " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano)")
    Double avgNotaByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT FUNCTION('MONTH', r.dataFinalizacao) as mes, COUNT(r) as total " +
           "FROM RegistroModel r WHERE r.usuario.id = :usuarioId " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano) " +
           "AND r.dataFinalizacao IS NOT NULL " +
           "GROUP BY FUNCTION('MONTH', r.dataFinalizacao) " +
           "ORDER BY FUNCTION('MONTH', r.dataFinalizacao)")
    List<Object[]> obterDadosMensais(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT j.genero, COUNT(r) FROM RegistroModel r JOIN r.jogo j " +
           "WHERE r.usuario.id = :usuarioId " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano) " +
           "GROUP BY j.genero")
    List<Object[]> countJogosByGenero(@Param("usuarioId") Long usuarioId, @Param("ano") Integer ano);

    @Query("SELECT DISTINCT FUNCTION('YEAR', r.dataFinalizacao) FROM RegistroModel r " +
           "WHERE r.usuario.id = :usuarioId AND r.dataFinalizacao IS NOT NULL " +
           "ORDER BY FUNCTION('YEAR', r.dataFinalizacao) DESC")
    List<Integer> findAnosComRegistros(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r.jogo.nome, AVG(r.nota) as notaMedia FROM RegistroModel r " +
           "WHERE r.usuario.id = :usuarioId AND r.nota IS NOT NULL " +
           "AND (:ano IS NULL OR FUNCTION('YEAR', r.dataFinalizacao) = :ano) " +
           "GROUP BY r.jogo.nome ORDER BY notaMedia DESC")
    List<Object[]> findTopJogosByUsuarioId(@Param("usuarioId") Long usuarioId, 
                                           Pageable pageable,
                                          @Param("ano") Integer ano);
}
