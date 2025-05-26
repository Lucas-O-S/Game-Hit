package br.edu.cefsa.gametracker.Controller;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.cefsa.gametracker.Enum.Estado;
import br.edu.cefsa.gametracker.Model.RegistroModel;
import br.edu.cefsa.gametracker.Model.UsuarioModel;
import br.edu.cefsa.gametracker.service.JogoService;
import br.edu.cefsa.gametracker.service.RegistroService;
import br.edu.cefsa.gametracker.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Registro")
public class RegistroController extends PadraoAssociativaController<RegistroModel> {

    @Autowired
    RegistroService registroService;

    @Autowired
    JogoService jogoService;

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping("/Cadastro")
    public String Cadastro(Model valores, @RequestParam("jogoId") Long jogoId) {
        valores.addAttribute("jogo", jogoService.BuscarPorId(jogoId));
        valores.addAttribute("operacao", 'I');
        valores.addAttribute("erro", "");
        valores.addAttribute("registro", new RegistroModel());
        valores.addAttribute("estados", Estado.values());
        return "Registro/Form";

    }

    @Override
    @RequestMapping("/Editar")
    public String Editar(Model valores,  @RequestParam("id")  Long id,  @RequestParam("jogoId")  long jogoId) {
        valores.addAttribute("jogo", jogoService.BuscarPorId(jogoId));
        valores.addAttribute("operacao", 'E');
        valores.addAttribute("erro", "");
        valores.addAttribute("registro", registroService.BuscarPorId(id));
        valores.addAttribute("estados", Estado.values());
        
        return "Registro/Form";

    }

    @Override
    public boolean Validar(RegistroModel model, char operacao, Model valores) {
        if(model.getDataFinalizacao() == null){
            model.setDataFinalizacao( LocalDate.of(1753, 1, 1));
        }
        return true;
        
    }

    @Override
    @PostMapping("/Excluir")
    public String Excluir(Model valores, @RequestParam("id") long id) {
        try {
            RegistroModel model = registroService.BuscarPorId(id);
            registroService.Excluir(id);
                return "redirect:/Registro/Listar?id=" + model.getUsuario().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";

    }

    @PostMapping("/Save")
    public String Save(
        HttpSession session,
        RegistroModel model,
        Model valores,
        @RequestParam("operacao") char operacao
    ){
        try{
            if (Validar(model, model.getId() == null ? 'I' : 'E', valores)) {
                if(operacao == 'I'){
                    registroService.Inserir(model);

                }
                else{
                    registroService.Editar(model);
                }
                return "redirect:/Registro/Listar?id=" + model.getUsuario().getId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

    @GetMapping("/Listar")
    public String listarRegistros(
            @RequestParam(value = "jogoNome", required = false) String jogoNome,
            @RequestParam(value = "estado", required = false) Estado estado,
            @RequestParam(value = "minNota", required = false) Integer minNota,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam("id") Long usuarioId,
            Model model) {
        
        try {
            Pageable pageable = PageRequest.of(page - 1, 10);
            
            Page<RegistroModel> registrosPage = registroService.filtrarRegistros(
                    usuarioId, 
                    jogoNome,
                    estado,
                    minNota,
                    pageable);
            
            model.addAttribute("registros", registrosPage.getContent());
            model.addAttribute("totalPaginas", registrosPage.getTotalPages());
            model.addAttribute("paginaAtual", page);
            model.addAttribute("estados", Estado.values());
            model.addAttribute("filtros", buildFiltrosString(jogoNome, estado, minNota));
            model.addAttribute("usuario", usuarioService.BuscarPorId(usuarioId));

            return "Registro/Lista";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Ocorreu um erro ao carregar os registros");
            return "redirect:/Usuario/Perfil";
        }
    }

    private String buildFiltrosString(String jogoNome, Estado estado, Integer minNota) {
        StringBuilder filtros = new StringBuilder();
        
        if (jogoNome != null && !jogoNome.isEmpty()) {
            filtros.append("&jogoNome=").append(jogoNome);
        }
        if (estado != null) {
            filtros.append("&estado=").append(estado);
        }
        if (minNota != null) {
            filtros.append("&minNota=").append(minNota);
        }
        
        return filtros.toString();
    }

    @GetMapping("/Status")
    public String statusDashboard(
        @RequestParam("id") Long usuarioId,
        @RequestParam(value = "ano", required = false) Integer anoFiltro,
        Model model
    ) {
        try{
            UsuarioModel usuario = usuarioService.BuscarPorId(usuarioId);
            if (usuario == null) {
                model.addAttribute("erro", "Usuário não encontrado");
                return "redirect:/index";
            }
            model.addAttribute("usuario", usuario);
            Object estatisticas = registroService.obterEstatisticasUsuario(usuarioId, anoFiltro);
            if (estatisticas == null) estatisticas = new java.util.HashMap<>();
            model.addAttribute("estatisticas", estatisticas);

            Object jogosPorGenero = registroService.contarJogosPorGenero(usuarioId, anoFiltro);
            if (jogosPorGenero == null) jogosPorGenero = new java.util.HashMap<>();
            model.addAttribute("jogosPorGenero", jogosPorGenero);
            
            Object jogosPorEstado = registroService.contarJogosPorEstado(usuarioId, anoFiltro);
            if (jogosPorEstado == null) jogosPorEstado = new java.util.HashMap<>();
            model.addAttribute("jogosPorEstado", jogosPorEstado);

            Object anosComRegistros = registroService.obterAnosComRegistros(usuarioId, anoFiltro);
            if (anosComRegistros == null) anosComRegistros = new java.util.ArrayList<>();
            model.addAttribute("anosComRegistros", anosComRegistros);

            model.addAttribute("topJogos", registroService.buscarTopJogos(usuarioId, 5, anoFiltro));
            return "Registro/Status";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Ocorreu um erro ao carregar os registros");
            return "redirect:/Usuario/Perfil?id=" + usuarioId;
        }

    }


    @Override
    protected String Buscar(Model valores, String valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Buscar'");
    }


}
