package br.edu.cefsa.gametracker.Interfaces;

import java.util.List;

public interface InterfaceService <T> {
    public void salvar(T t);
    public void editar(T t);
    public void excluir(Long id);
    public Object buscarPorId(Long id);
    public List<Object> listarTodos();
}
