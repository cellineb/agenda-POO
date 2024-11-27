package trabalhoPOO.agenda.persistence;

import java.util.List;

public interface IDisciplinaDAO {

    void inserir(Disciplina d) throws AgendaException;

    void atualizar(Disciplina d) throws AgendaException;

    void remover(Disciplina d) throws AgendaException;

    List<Disciplina> pesquisarPorNome(String nome) throws AgendaException;

}
