package br.edu.cefsa.gametracker.Interfaces;

import java.util.List;

public interface InterfaceService <T> {
    public void Inserir(T t);
    public void Editar(T t);
    public void Excluir(Long id);
    public T BuscarPorId(Long id);
    public List<T> ListarTodos();
}
