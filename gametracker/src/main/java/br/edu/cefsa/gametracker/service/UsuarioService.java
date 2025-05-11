package br.edu.cefsa.gametracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Interfaces.InterfaceService;
import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.Repository.UsuarioRepository;

@Service // Anotação para indicar que esta classe é um serviço
public class UsuarioService implements InterfaceService<UsuarioModel> {

    @Autowired // Injeção de dependência do repositório
    UsuarioRepository usuarioRepository;

    @Override
    public void Inserir(UsuarioModel model) {
        usuarioRepository.save(model);
    }

    @Override
    public void Editar(UsuarioModel model) {

        if(usuarioRepository.existsById(model.getId())){
            usuarioRepository.save(model);

        }

    }

    @Override
    public void Excluir(Long id) {
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);

        }    
    }

    @Override
    public UsuarioModel BuscarPorId(Long id) {
        if(usuarioRepository.existsById(id)){
            return usuarioRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public List<UsuarioModel> ListarTodos() {
        return usuarioRepository.findAll();
    }

    public boolean VerificarEmail(String email){
        return usuarioRepository.existsByEmail(email) != null;
    }

}
