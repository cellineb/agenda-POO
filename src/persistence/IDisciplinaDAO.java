package persistence;

import java.util.List;

import model.Disciplina;
import tools.AgendaException;

public interface IDisciplinaDAO {

    void inserir(Disciplina d) throws AgendaException;

    void atualizar(Disciplina d) throws AgendaException;

    void remover(Disciplina d) throws AgendaException;

    List<Disciplina> pesquisarPorNome(String nome) throws AgendaException;

}
