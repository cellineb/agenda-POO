package persistence;

import java.util.List;

import model.Tarefa;
import tools.AgendaException;

public interface ITarefaDAO {

    void inserir(Tarefa t) throws AgendaException;

    void atualizar(Tarefa t) throws AgendaException;

    void remover(Tarefa t) throws AgendaException;

    List<Tarefa> pesquisarPorNome(String nome) throws AgendaException;

}
