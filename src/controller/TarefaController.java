package controller;

import persistence.ITarefaDAO;
import persistence.TarefaDAOImpl;
import tools.AgendaException;
import model.Tarefa;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TarefaController {

    private ObservableList<Tarefa> lista = FXCollections.observableArrayList();
    private int contador = 2;

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty data = new SimpleStringProperty("");
    private StringProperty hora = new SimpleStringProperty("");
    private StringProperty tipoTarefa = new SimpleStringProperty("");
    private StringProperty disciplina = new SimpleStringProperty("");

    private ITarefaDAO tarefaDAO = null;

    public TarefaController() throws AgendaException {
        tarefaDAO = new TarefaDAOImpl();
    }

    public Tarefa paraEntidade() {
        Tarefa t = new Tarefa();
        t.setId(id.get());
        t.setNome(nome.get());
        t.setData(data.get());
        t.setHora(hora.get());
        t.setTipoTarefa(tipoTarefa.get());
        t.setDisciplina(disciplina.get());
        return t;
    }

    public void excluir(Tarefa t) throws AgendaException {
        tarefaDAO.remover(t);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        nome.set("");
        data.set("");
        hora.set("");
        tipoTarefa.set("");
        disciplina.set("");
    }

    public void paraTela(Tarefa t) {
        if (t != null) {
            id.set(t.getId());
            nome.set(t.getNome());
            data.set(t.getData());
            hora.set(t.getHora());
            tipoTarefa.set(t.getTipoTarefa());
            disciplina.set(t.getDisciplina());
        }
    }

    public void gravar() throws AgendaException {
        Tarefa t = paraEntidade();
        if (t.getId() == 0) {
            this.contador += 1;
            t.setId(this.contador);
            tarefaDAO.inserir(t);
        } else {
            tarefaDAO.atualizar(t);
        }
        pesquisarTodos();
    }

    public void pesquisar() throws AgendaException {
        lista.clear();
        lista.addAll(tarefaDAO.pesquisarPorNome(nome.get()));
    }

    public void pesquisarTodos() throws AgendaException {
        lista.clear();
        lista.addAll(tarefaDAO.pesquisarPorNome(""));
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public StringProperty dataProperty() {
        return this.data;
    }

    public StringProperty horaProperty() {
        return this.hora;
    }

    public StringProperty tipoTarefaProperty() {
        return this.tipoTarefa;
    }

    public StringProperty disciplinaProperty(TableView<Tarefa> tableView) {
        return this.disciplina;
    }

    public ObservableList<Tarefa> getLista() {
        return this.lista;
    }
}
