package br.edu.cefsa.gametracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.cefsa.gametracker.Enum.Estado;
import br.edu.cefsa.gametracker.Enum.Genero;
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


    public Page<RegistroModel> filtrarRegistros(
            Long usuarioId,
            String jogoNome, 
            Estado estado, 
            Integer minNota, 
            Pageable pageable) {
        
        Page<RegistroModel> registros = registroRepository.findByFilters(usuarioId, jogoNome, estado, minNota, pageable);
        
        registros.getContent().forEach(registro -> {
            if(registro.getJogo().getFotoByte() == null){
                    ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultGameImage.png");
                try {
                    registro.getJogo().setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));
                } 
                catch (IOException ex) {

                }
            }
                String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(registro.getJogo().getFotoByte());
                registro.getJogo().setFotoBase64(base64Image);
        });
        
        return registros;
    }

     public Map<String, Object> obterEstatisticasUsuario(Long usuarioId, Integer ano) {
    Map<String, Object> estatisticas = new HashMap<>();
        
        try {
            // Total de jogos
            Long totalJogos = registroRepository.countByUsuarioId(usuarioId, ano);
            estatisticas.put("totalJogos", totalJogos != null ? totalJogos : 0L);
            
            // Jogos finalizados (COMPLETO ou FINALIZADO dependendo do seu enum)
            Long jogosFinalizados = 0L;
            List<Object[]> resultadosEstado = registroRepository.countByEstadoGrouped(usuarioId, ano);
            
            if (resultadosEstado != null) {
                jogosFinalizados = resultadosEstado.stream()
                    .filter(Objects::nonNull)
                    .filter(arr -> arr.length >= 2 && arr[0] != null && arr[1] != null)
                    .filter(arr -> Estado.COMPLETO.equals(arr[0])) // Ajuste para o nome correto do enum
                    .map(arr -> (Long) arr[1])
                    .findFirst()
                    .orElse(0L);
            }
            estatisticas.put("jogosFinalizados", jogosFinalizados);
            
            // Tempo total jogado
            Long tempoTotalSegundos = registroRepository.sumTempoJogoByUsuarioId(usuarioId, ano);
            Duration tempoTotal = null;
            if (tempoTotalSegundos != null) {
                tempoTotal = Duration.ofSeconds(tempoTotalSegundos);
            }
            estatisticas.put("tempoTotal", tempoTotal != null ? formatDuration(tempoTotal) : "0h");
            
            // Média de notas (com tratamento para divisão por zero)
            Double mediaNotas = registroRepository.avgNotaByUsuarioId(usuarioId, ano);
            estatisticas.put("mediaNotas", mediaNotas != null ? 
                Math.round(mediaNotas * 10.0) / 10.0 : 0.0); // Arredonda para 1 decimal
            
            // Dados mensais para gráfico
            List<Object[]> dadosMensais = registroRepository.obterDadosMensais(usuarioId, ano);
            estatisticas.put("dadosMensais", processarDadosMensais(dadosMensais));
            
        } catch (Exception e) {
            // Log do erro (substitua pelo seu logger)
            System.err.println("Erro ao obter estatísticas do usuário " + usuarioId + ": " + e.getMessage());
            
            // Valores padrão em caso de erro
            estatisticas.put("totalJogos", 0L);
            estatisticas.put("jogosFinalizados", 0L);
            estatisticas.put("tempoTotal", "0h");
            estatisticas.put("mediaNotas", 0.0);
            estatisticas.put("dadosMensais", new int[12]); // Array vazio para 12 meses
        }
        
        return estatisticas;
    }

    public Map<Genero, Long> contarJogosPorGenero(Long usuarioId, Integer ano) {
        List<Object[]> resultados = registroRepository.countJogosByGenero(usuarioId, ano);
        return resultados.stream()
            .collect(Collectors.toMap(
                arr -> (Genero) arr[0],
                arr -> (Long) arr[1]
            ));
    }

    public Map<Estado, Long> contarJogosPorEstado(Long usuarioId, Integer ano) {
        List<Object[]> resultados = registroRepository.countByEstadoGrouped(usuarioId, ano);
        return resultados.stream()
            .collect(Collectors.toMap(
                arr -> (Estado) arr[0],
                arr -> (Long) arr[1]
            ));
    }

    public List<Integer> obterAnosComRegistros(Long usuarioId, Integer ano) {
        return registroRepository.findAnosComRegistros(usuarioId);
    }

    private int[] processarDadosMensais(List<Object[]> dadosMensais) {
        int[] meses = new int[12]; // Inicializa com zeros
        
        for (Object[] dado : dadosMensais) {
            int mes = (int) dado[0];
            long total = (long) dado[1];
            meses[mes - 1] = (int) total; // Janeiro = 1 → índice 0
        }
        
        return meses;
    }

    private String formatDuration(Duration duration) {
        if (duration == null) return "0h";
        long horas = duration.toHours();
        long minutos = duration.toMinutes() % 60;
        return String.format("%dh %02dm", horas, minutos);
    }

    public List<Map<String, Object>> buscarTopJogos(Long usuarioId, int limite, Integer ano) {
        List<Object[]> results = registroRepository.findTopJogosByUsuarioId(usuarioId, org.springframework.data.domain.PageRequest.of(0, limite), ano);
        List<Map<String, Object>> topJogos = new java.util.ArrayList<>();
        for (Object[] arr : results) {
            Map<String, Object> jogo = new java.util.HashMap<>();
            jogo.put("nome", arr[0]);
            jogo.put("notaMedia", arr[1]);
            topJogos.add(jogo);
        }
        return topJogos;
    }

    public Integer TotalRegistros() {
        return (int) registroRepository.count();
    }
}
