package view;

import controller.TarefaController;
import model.Tarefa;
import tools.AgendaException;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class TarefaBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtData = new TextField();
    private TextField txtHora = new TextField();
    private TextField txtTipoTarefa = new TextField();
    private TextField txtDisciplina = new TextField();

    private TarefaController control = null;

    private TableView<Tarefa> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new TarefaController();
        } catch (AgendaException e) {
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (AgendaException err) {
                new Alert(AlertType.ERROR, "Erro ao gravar a Tarefa", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (AgendaException err) {
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("*");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Data: "), 0, 2);
        paneForm.add(txtData, 1, 2);
        paneForm.add(new Label("Hora: "), 0, 3);
        paneForm.add(txtHora, 1, 3);
        paneForm.add(new Label("Tipo da Tarefa: "), 0, 4);
        paneForm.add(txtTipoTarefa, 1, 4);
        paneForm.add(new Label("Disciplina: "), 0, 5);
        paneForm.add(txtDisciplina, 1, 5);

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        TableColumn<Tarefa, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Tarefa, Integer>("id"));

        TableColumn<Tarefa, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("nome"));

        TableColumn<Tarefa, String> col3 = new TableColumn<>("Data");
        col3.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("data"));

        TableColumn<Tarefa, String> col4 = new TableColumn<>("Hora");
        col4.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("hora"));

        TableColumn<Tarefa, String> col5 = new TableColumn<>("Tipo da Tarefa");
        col5.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("tipoTarefa"));

        TableColumn<Tarefa, String> col6 = new TableColumn<>("Disciplina");
        col6.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("disciplina"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Nome: " + novo.getNome());
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Tarefa, Void>, TableCell<Tarefa, Void>> cb = new Callback<>() {
            @Override
            public TableCell<Tarefa, Void> call(
                    TableColumn<Tarefa, Void> param) {
                TableCell<Tarefa, Void> celula = new TableCell<>() {
                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Tarefa tarefa = tableView.getItems().get(getIndex());
                            try {
                                control.excluir(tarefa);
                            } catch (AgendaException err) {
                                new Alert(AlertType.ERROR, "Erro ao excluir tarefa", ButtonType.OK).showAndWait();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        if (!empty) {
                            setGraphic(btnApagar);
                        } else {
                            setGraphic(null);
                        }
                    }

                };
                return celula;
            }
        };

        TableColumn<Tarefa, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        tableView.setItems(control.getLista());

    }

    public void ligacoes() {
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.dataProperty(), txtData.textProperty());
        Bindings.bindBidirectional(control.horaProperty(), txtHora.textProperty());
        Bindings.bindBidirectional(control.tipoTarefaProperty(), txtTipoTarefa.textProperty());
        Bindings.bindBidirectional(control.disciplinaProperty(), txtDisciplina.textProperty());
    }
}