package br.edu.cefsa.gametracker.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Enum.Estado;
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

    public List<RegistroModel> BuscarPorNomeJogo(String nomeJogo) {
        return registroRepository.findByJogoNomeContainingIgnoreCase(nomeJogo);
        
    }
    public Page<RegistroModel> filtrarRegistros(
            Long usuarioId,
            String jogoNome, 
            Estado estado, 
            Integer minNota, 
            Pageable pageable) {
        
        Page<RegistroModel> registros = registroRepository.findByFilters(usuarioId, jogoNome, estado, minNota, pageable);
        
        registros.getContent().forEach(registro -> {
            if (registro.getJogo() != null && registro.getJogo().getFotoByte() != null) {
                // Já monta a string completa aqui no service
                String base64Image = "data:image/png;base64," + 
                    Base64.getEncoder().encodeToString(registro.getJogo().getFotoByte());
                registro.getJogo().setFotoBase64(base64Image);
            } else {
                registro.getJogo().setFotoBase64("/images/default-game.png");
            }
        });
        
        return registros;
    }

}
