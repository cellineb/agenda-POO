package view;

import controller.DisciplinaController;
import model.Disciplina;
import tools.AgendaException;

import javafx.beans.binding.Bindings;
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

public class DisciplinaBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtProfessor = new TextField();

    private DisciplinaController control = null;
    private TableView<Disciplina> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new DisciplinaController();
        } catch (AgendaException e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }

        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (AgendaException err) {
                new Alert(AlertType.ERROR, "Erro ao gravar a disciplina", ButtonType.OK).showAndWait();
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
        paneForm.add(new Label("Professor: "), 0, 2);
        paneForm.add(txtProfessor, 1, 2);

        paneForm.add(btnGravar, 0, 3);
        paneForm.add(btnPesquisar, 1, 3);

        
        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        TableColumn<Disciplina, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Disciplina, Integer>("id"));

        TableColumn<Disciplina, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(new PropertyValueFactory<Disciplina, String>("nome"));

        TableColumn<Disciplina, String> col3 = new TableColumn<>("Professor");
        col3.setCellValueFactory(new PropertyValueFactory<Disciplina, String>("professor"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        System.out.println("Nome: " + novo.getNome());
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Disciplina, Void>, TableCell<Disciplina, Void>> cb = new Callback<>() {
            @Override
            public TableCell<Disciplina, Void> call(
                    TableColumn<Disciplina, Void> param) {
                TableCell<Disciplina, Void> celula = new TableCell<>() {
                    final Button btnApagar = new Button("Apagar");

                    {
                        btnApagar.setOnAction(e -> {
                            Disciplina disciplina = tableView.getItems().get(getIndex());
                            try {
                                control.excluir(disciplina);
                            } catch (AgendaException err) {
                                new Alert(AlertType.ERROR, "Erro ao excluir a disciplina", ButtonType.OK).showAndWait();
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

        TableColumn<Disciplina, Void> col6 = new TableColumn<>("Ação");
        col6.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.professorProperty(), txtProfessor.textProperty());
    }
}
