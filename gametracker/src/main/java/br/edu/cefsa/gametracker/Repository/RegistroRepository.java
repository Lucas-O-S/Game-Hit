package br.edu.cefsa.gametracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.cefsa.gametracker.Model.RegistroModel;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroModel, Long> {



}
