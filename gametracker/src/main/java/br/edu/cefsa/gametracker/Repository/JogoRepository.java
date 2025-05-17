package br.edu.cefsa.gametracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.cefsa.gametracker.Model.JogoModel;

public interface JogoRepository extends JpaRepository<JogoModel, Long>{
    public Boolean existByNome();
}
