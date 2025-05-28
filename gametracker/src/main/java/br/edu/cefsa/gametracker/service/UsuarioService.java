package br.edu.cefsa.gametracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Interfaces.InterfaceService;
import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.Repository.UsuarioRepository;

@Service // Anotação para indicar que esta classe é um serviço
public class UsuarioService implements InterfaceService<UsuarioModel> {

    @Autowired // Injeção de dependência do repositório
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    //Só inseri um usuario
    @Override
    public void Inserir(UsuarioModel model) {
        model.setSenha(passwordEncoder.encode(model.getSenha()));
        usuarioRepository.save(model);
    }

    //
    @Override
    public void Editar(UsuarioModel model) {
        if(usuarioRepository.existsById(model.getId())){
            model.setSenha(passwordEncoder.encode(model.getSenha()));
            usuarioRepository.save(model);
        }

    }

    public UsuarioModel Login(String email, String senha) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            // Compara a senha em texto com a senha criptografada
            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                return usuario;
            }
        }
        return null;
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

            if(usuario.getFotoByte() == null){
                ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultUserImage.png");
                try {
                    usuario.setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));
                } catch (IOException ex) {

                }
            }

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
            if(usuario.getFotoByte() != null){
                usuario.setFotoBase64(java.util.Base64.getEncoder().encodeToString(usuario.getFotoByte()));

            }

        }
        return usuarios;
    }

    //Verifica se o email existe
    public boolean VerificarEmail(String email){
        return usuarioRepository.existsByEmail(email);
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

    public UsuarioModel BuscarEmail(String email){
        return usuarioRepository.findByEmail(email);
    }


    //Retorna numero total de usuarios[0], usuarios normais[1] e adm[2]
    public List<Integer> TotalUsuarios(){
        List<Integer> lista = new ArrayList<>();
        lista.add((int) usuarioRepository.count());
        lista.add((int) usuarioRepository.countByAdm(false));
        lista.add((int) usuarioRepository.countByAdm(true));
        
        return lista;
    }

    public Map<Integer, Integer> UsuariosUltimoAno() {
        Map<Integer, Integer> usuariosUltimoAno = new HashMap<>();
        List<Object[]> dados = usuarioRepository.usuariosUltimoAno(
            java.time.LocalDateTime.now().minusYears(1)
        );
    
        for (int i = 1; i <= 12; i++) {
            Integer mes = i;
            Integer qnt = 0;
    
            for (Object[] dado : dados) {
                if (dado[0] != null && ((Integer) dado[0]).equals(i)) {
                    qnt = ((Number) dado[1]).intValue();
                    break; 
                }
            }
    
            usuariosUltimoAno.put(mes, qnt);
        }
    
        return usuariosUltimoAno;
    }
    

    
}
