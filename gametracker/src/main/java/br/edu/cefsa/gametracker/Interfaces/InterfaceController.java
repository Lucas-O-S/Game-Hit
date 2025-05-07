package br.edu.cefsa.gametracker.Interfaces;

import java.util.List;

//Interface generica para as controllers
public interface  InterfaceController <T>{

    
    public void salvar(T t);
    public void editar(T t);
    public void excluir(Long id);
    public T buscarPorId(Long id);
    public List<T> listarTodos();

}
