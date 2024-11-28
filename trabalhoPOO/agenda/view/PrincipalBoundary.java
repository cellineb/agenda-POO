package trabalhoPOO.agenda;

import java.util.HashMap;
import java.util.Map;

import trabalhoPOO.agenda.view.DisciplinaBoundary;
import trabalhoPOO.agenda.view.TarefaBoundary;

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
        Menu mnuAjuda = new Menu("Ajuda");
        MenuItem mnuItemDisciplina = new MenuItem("Disciplina");
        mnuItemContato.setOnAction(e -> panePrincipal.setCenter(telas.get("disciplina").render()));
        MenuItem mnuItemTarefa = new MenuItem("Tarefa");
        mnuItemEndereco.setOnAction(e -> panePrincipal.setCenter(telas.get("tarefa").render()));
        MenuItem mnuItemCreditos = new MenuItem("Creditos");
        mnuCadastro.getItems().addAll(mnuItemDisciplina);
        mnuCadastro.getItems().addAll(mnuItemTarefa);
        mnuAjuda.getItems().add(mnuItemCreditos);
        menuBar.getMenus().addAll(mnuCadastro, mnuAjuda);
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
