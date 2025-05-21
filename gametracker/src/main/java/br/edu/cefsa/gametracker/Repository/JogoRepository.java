package br.edu.cefsa.gametracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.cefsa.gametracker.Model.JogoModel;

@Repository
public interface JogoRepository extends JpaRepository<JogoModel, Long>{
    public Boolean existsByNome(String nome);
}
