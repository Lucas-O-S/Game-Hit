package br.edu.cefsa.gametracker.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.cefsa.gametracker.Interfaces.InterfaceController;
import br.edu.cefsa.gametracker.Repository.UsuarioRepository;

@Controller
public class UsuarioController implements InterfaceController<Object>{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Object buscarPorId(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void editar(Object t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void excluir(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Object> listarTodos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void salvar(Object t) {
        // TODO Auto-generated method stub
        
    }

}
