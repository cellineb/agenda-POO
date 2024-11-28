package trabalhoPOO.agenda;

import trabalhoPOO.agenda.persistence.DisciplinaDAO;
import trabalhoPOO.agenda.persistence.DisciplinaDAOImpl;
import trabalhoPOO.agenda.model.Disciplina;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DisciplinaController {

    private ObservableList<Disciplina> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty professor = new SimpleStringProperty("");

    private DisciplinaDAO disciplinaDAO = null;

    public DisciplinaController() throws AgendaException {
        disciplinaDAO = new DisciplinaDAOImpl();
    }

    public Disciplina paraEntidade() {
        Disciplina d = new Disciplina();
        c.setId(id.get());
        c.setNome(nome.get());
        d.setProfessor(professor.get());
        return d;
    }

    public void excluir(Disciplina d) throws AgendaException {
        disciplinaDAO.remover(d);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        nome.set("");
        professor.set("");
    }

    public void paraTela(Disciplina d) {
        if (d != null) {
            id.set(d.getId());
            nome.set(d.getNome());
            professor.set(d.getProfessor());
        }
    }

    public void gravar() throws AgendaException {
        Disciplina d = paraEntidade();
        if (d.getId() == 0) {
            this.contador += 1;
            d.setId(this.contador);
            disciplinaDAO.inserir(d);
        } else {
            disciplinaDAO.atualizar(d);
        }
        pesquisarTodos();
    }

    public void pesquisar() throws AgendaException {
        lista.clear();
        lista.addAll(disciplinaDAO.pesquisarPorNome(nome.get()));
    }

    public void pesquisarTodos() throws AgendaException {
        lista.clear();
        lista.addAll(disciplinaDAO.pesquisarPorNome(""));
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public StringProperty professorProperty() {
        return this.professor;
    }

    public ObservableList<Disciplina> getLista() {
        return this.lista;
    }
}
