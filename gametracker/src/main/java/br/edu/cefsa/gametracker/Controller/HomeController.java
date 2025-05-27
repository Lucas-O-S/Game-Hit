package br.edu.cefsa.gametracker.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.Repository.RegistroRepository;
import br.edu.cefsa.gametracker.service.JogoService;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    JogoService jogoService;

    @Autowired
    RegistroRepository registroRepository;

    //Manda para a index da home
    @RequestMapping("/index")
    public String index(Model model){
        List<JogoModel> jogos = jogoService.ListarTodos();
        // Jogos em destaque: aleatórios
        List<JogoModel> jogosDestaque = new java.util.ArrayList<>(jogos);
        java.util.Collections.shuffle(jogosDestaque, new Random());
        model.addAttribute("jogos", jogosDestaque.stream().limit(3).collect(Collectors.toList()));

        // Jogos recentes: pelo createdAt
        List<JogoModel> jogosRecentes = jogos.stream()
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
            .limit(4)
            .map(jogo -> {
                if (jogo.getFotoByte() == null || jogo.getFotoByte().length == 0) {
                    ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultGameImage.png");
                    try {
                        jogo.setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));
                    } catch (IOException ex) {}
                }
                jogo.setFotoBase64("data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(jogo.getFotoByte()));
                return jogo;
            })
            .collect(Collectors.toList());
        model.addAttribute("jogosRecentes", jogosRecentes);

        // Top jogos: média das maiores notas de todos os usuários
        Pageable pageable = PageRequest.of(0, 4); // agora retorna top 5
        List<Object[]> topJogosRaw = registroRepository.findTopJogosByMediaNota(pageable);
        List<JogoModel> jogosTop = new java.util.ArrayList<>();
        List<Double> mediasTop = new java.util.ArrayList<>();
        for (Object[] obj : topJogosRaw) {
            Long jogoId = (Long) obj[0];
            Double mediaNota = (Double) obj[1];
            JogoModel jogo = jogoService.BuscarPorId(jogoId);
            if (jogo != null) {
                if (jogo.getFotoByte() == null || jogo.getFotoByte().length == 0) {
                    ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultGameImage.png");
                    try {
                        jogo.setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));
                    } catch (IOException ex) {}
                }
                jogo.setFotoBase64("data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(jogo.getFotoByte()));
                jogosTop.add(jogo);
                mediasTop.add(mediaNota);
            }
        }
        model.addAttribute("jogosTop", jogosTop);
        model.addAttribute("mediasTop", mediasTop);
        return "home/index";
    }

    //Se não tiver nada na url manda para a index da home
    @RequestMapping("")
    public String RedirectToIndex(){
        return "redirect:/index";
    }
}
