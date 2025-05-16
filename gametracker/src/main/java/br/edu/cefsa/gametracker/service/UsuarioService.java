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

    //Só inseri um usuario
    @Override
    public void Inserir(UsuarioModel model) {
        usuarioRepository.save(model);
    }

    //
    @Override
    public void Editar(UsuarioModel model) {
        if(usuarioRepository.existsById(model.getId())){
            usuarioRepository.save(model);
        }

    }

    //Exclui o usuario caso exista
    @Override
    public void Excluir(Long id) {
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);

        }    
    }


    //Devolve o usuario com base no id
    @Override
    public UsuarioModel BuscarPorId(Long id) {
        if(usuarioRepository.existsById(id)){
            UsuarioModel usuario = usuarioRepository.findById(id).get();

            //Configura a imagem em base 64
            usuario.setFotoBase64(java.util.Base64.getEncoder().encodeToString(usuario.getFotoByte()));
            return usuario;
        }else{
            return null;
        }
    }

    //Busca todos os registros
    @Override
    public List<UsuarioModel> ListarTodos() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();

        //Configura as fotos em base 64
        for (UsuarioModel usuario : usuarios) {
            usuario.setFotoBase64(java.util.Base64.getEncoder().encodeToString(usuario.getFotoByte()));

        }
        return usuarios;
    }

    //Verifica se o email existe
    public boolean VerificarEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    //Devolve um usuario com email e senha enviados
    public UsuarioModel Login(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    //Lista todos os usuarios que contenha a string pedida
    public List<UsuarioModel> BuscarPorNome(String nome){
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    //Muda o status de adm do usuario com base nos parametros
    public void MudarAdm(boolean status, long id){
        if(usuarioRepository.findById(id) != null){
            UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow();
            usuario.setAdm(status);
            usuarioRepository.save(usuario);
        }

    }
}
