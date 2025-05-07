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
    public void salvar(UsuarioModel model) {
        usuarioRepository.save(model);
    }

    @Override
    public void editar(UsuarioModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void excluir(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object buscarPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
