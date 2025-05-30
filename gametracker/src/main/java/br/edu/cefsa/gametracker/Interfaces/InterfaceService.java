package br.edu.cefsa.gametracker.Interfaces;

import java.io.IOException;
import java.util.List;

//Interface para a camada de serviço generica 
public interface InterfaceService <T> {
    public void Inserir(T t);
    public void Editar(T t);
    public void Excluir(Long id);
    public T BuscarPorId(Long id) throws IOException;
    public List<T> ListarTodos();
}
