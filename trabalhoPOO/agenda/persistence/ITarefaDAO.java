package trabalhoPOO.agenda.persistence;

import java.util.List;

public interface ITarefaDAO {

    void inserir(Tarefa t) throws AgendaException;

    void atualizar(Tarefa t) throws AgendaException;

    void remover(Tarefa t) throws AgendaException;

    List<Tarefa> pesquisarPorNome(String nome) throws AgendaException;

}
