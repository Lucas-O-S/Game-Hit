package br.edu.cefsa.gametracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Interfaces.InterfaceService;
import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.Repository.JogoRepository;

@Service
public class JogoService implements InterfaceService<JogoModel>{

    @Autowired
    JogoRepository jogoRepository;

    @Override
    public JogoModel BuscarPorId(Long id) {
        if(jogoRepository.existsById(id)){
            JogoModel model = jogoRepository.findById(id).get();
            model.setFotoBase64(java.util.Base64.getEncoder().encodeToString(model.getFotoByte()));
            return model;
        }
        return null;
    }

    @Override
    public void Editar(JogoModel model) {
        if(jogoRepository.existsById(model.getId())){
            model.setFotoBase64(java.util.Base64.getEncoder().encodeToString(model.getFotoByte()));

            jogoRepository.save(model);
        }
        
    }

    @Override
    public void Excluir(Long id) {
        if(jogoRepository.existsById(id)){
            jogoRepository.deleteById(id);
        }
    }

    @Override
    public void Inserir(JogoModel model) {
        jogoRepository.save(model);
        
    }

    @Override
    public List<JogoModel> ListarTodos() {
        List<JogoModel> lista = jogoRepository.findAll();
        return lista;
    }

    public Boolean existeNome(String nome) {
        return jogoRepository.existsByNome(nome);
    }
    
}
