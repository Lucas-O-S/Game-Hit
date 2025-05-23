package br.edu.cefsa.gametracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Interfaces.InterfaceService;
import br.edu.cefsa.gametracker.Model.RegistroModel;
import br.edu.cefsa.gametracker.Repository.RegistroRepository;

@Service
public class RegistroService implements InterfaceService<RegistroModel>{

    @Autowired
    RegistroRepository registroRepository;

    @Override
    public void Inserir(RegistroModel t) {
        registroRepository.save(t);
        
    }

    @Override
    public void Editar(RegistroModel t) {
        if(registroRepository.existsById(t.getId())){
            registroRepository.save(t);
        }
    }

    @Override
    public void Excluir(Long id) {
        if(registroRepository.existsById(id)){
            registroRepository.deleteById(id);
        }
    }

    @Override
    public RegistroModel BuscarPorId(Long id) {
        return registroRepository.findById(id).orElse(null);
    }

    @Override
    public List<RegistroModel> ListarTodos() {
        return registroRepository.findAll();
    }

}
