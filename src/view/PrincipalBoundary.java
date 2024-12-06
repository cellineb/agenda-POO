package view;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {
    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        telas.put("disciplina", new DisciplinaBoundary());
        telas.put("tarefa", new TarefaBoundary());
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        MenuItem mnuItemDisciplina = new MenuItem("Disciplina");
        mnuItemDisciplina.setOnAction(e -> panePrincipal.setCenter(telas.get("disciplina").render()));
        MenuItem mnuItemTarefa = new MenuItem("Tarefa");
        mnuItemTarefa.setOnAction(e -> panePrincipal.setCenter(telas.get("tarefa").render()));
        mnuCadastro.getItems().addAll(mnuItemDisciplina);
        mnuCadastro.getItems().addAll(mnuItemTarefa);
        menuBar.getMenus().addAll(mnuCadastro);
        panePrincipal.setTop(menuBar);
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Agenda para Estudantes");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }

}
