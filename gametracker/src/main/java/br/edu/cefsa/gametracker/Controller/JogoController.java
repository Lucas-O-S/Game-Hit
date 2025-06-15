package br.edu.cefsa.gametracker.Controller;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.edu.cefsa.gametracker.Enum.Genero;
import br.edu.cefsa.gametracker.Model.JogoModel;
import br.edu.cefsa.gametracker.service.JogoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Jogo")
public class JogoController extends PadraoController<JogoModel> {

    @Autowired
    JogoService jogoService;

    @Override
    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores) {
            valores.addAttribute("jogo", new JogoModel());
            valores.addAttribute("operacao", 'I');
            valores.addAttribute("erro", "");
            valores.addAttribute("generos", Genero.values());

            return "Jogo/Form";
    }

    @Override
    @RequestMapping("/Editar")
    public String Editar(Model valores, Long id) {
        try {
            valores.addAttribute("jogo", jogoService.BuscarPorId(id));
            valores.addAttribute("operacao", 'E');
            valores.addAttribute("erro", "");
            valores.addAttribute("generos", Genero.values());
            return "Jogo/Form";
        } catch (Exception e) {
            e.printStackTrace();
        }
            return "redirect:/index";
     }

    @Override
    protected boolean Validar(JogoModel model, char operacao, Model valores) {
        if(model.getNome() == null || model.getNome().isEmpty()) {
            valores.addAttribute("erro", "O nome do jogo não pode ser vazio");
            return false;
        }
        if (jogoService.existeNome(model.getNome()) && operacao == 'I') {
            valores.addAttribute("erro", "Já existe um jogo cadastrado com esse nome");
            return false;
        }

        if (model.getGenero() == null || model.getGenero().toString().isEmpty()) {
            valores.addAttribute("erro", "Você deve selecionar um gênero para o jogo");
            return false;
        }

        if(model.getFotoByte().length > 1048576 * 500) { 
            valores.addAttribute("erro", "A imagem do jogo não pode ser maior que 500MB");
            return false;

        }

        
        
        return true;
    }

    @Override
    @PostMapping("/Excluir")
    public String Excluir(HttpSession session, long id) {
        try {

            jogoService.Excluir(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
            return "redirect:/index";
    }

    @Override
    @GetMapping("/Buscar")
    public String Buscar(Model valores, @RequestParam(name = "nomeBuscar", required = false) String nome){
        try {
            List<JogoModel> lista = new ArrayList<>();
            if (nome != null && !nome.isEmpty()) {
                lista = jogoService.BuscarPorNome(nome);
                

            } else {
                lista = jogoService.ListarTodos();
            }
            valores.addAttribute("jogos", lista);
            return "/Jogo/busca";
        }
         catch (Exception e) {
            e.printStackTrace();

        }
        return "/";
    }
    

    @PostMapping("/Salvar")
    public String Salvar(HttpSession session,
     JogoModel model, Model valores,
    @RequestParam("imagem") MultipartFile imagem,
    @RequestParam("operacao") char operacao
    ) {
        try {

            //Verifica se uma imagem foi enviada, caso não ira ser usado uma default
            if (imagem != null && !imagem.isEmpty()) {
                model.setFotoByte(imagem.getBytes());
            } else {
                // Carrega imagem padrão apenas se for novo jogo
                if (model.getId() == null) { 
                    ClassPathResource imgFile = new ClassPathResource("static/IMG/DefaultGameImage.png");
                    model.setFotoByte(Files.readAllBytes(imgFile.getFile().toPath()));
                }
            }

            if (Validar(model, model.getId() == null ? 'I' : 'E', valores)) {


                if (model.getId() == null) {
                    jogoService.Inserir(model);
                } else {
                    jogoService.Editar(model);
                }
                return "redirect:/index";
                
            }
            else{
                valores.addAttribute("jogo", model);
                valores.addAttribute("operacao", operacao);
                valores.addAttribute("generos", Genero.values());


                return "/Jogo/Form";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

}
