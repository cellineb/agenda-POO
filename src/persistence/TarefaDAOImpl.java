package persistence;

import model.Tarefa;
import tools.AgendaException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAOImpl implements ITarefaDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/agenda";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public TarefaDAOImpl() throws AgendaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new AgendaException(e);
        }
    }

    @Override
    public void inserir(Tarefa t) throws AgendaException {
        try {
            String sql = """
                    INSERT INTO tarefa (id, nome, data, hora, tipoTarefa, disciplina)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, t.getId());
            stm.setString(2, t.getNome());
            stm.setString(3, t.getData());
            stm.setString(4, t.getHora());
            stm.setString(5, t.getTipoTarefa());
            stm.setString(6, t.getDisciplina());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public void atualizar(Tarefa t) throws AgendaException {
        try {
            String sql = """
                    UPDATE tarefa SET id=?, nome=?, data=?, hora=?, tipoTarefa=?, disciplina=?)
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, t.getId());
            stm.setString(2, t.getNome());
            java.sql.Date dt = java.sql.Date.valueOf(t.getData());
            stm.setDate(3, dt);
            stm.setString(4, t.getHora());
            stm.setString(5, t.getTipoTarefa());
            stm.setString(6, t.getDisciplina());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public void remover(Tarefa t) throws AgendaException {
        try {
            String sql = """
                    DELETE FROM tarefa
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, t.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public List<Tarefa> pesquisarPorNome(String nome) throws AgendaException {
        List<Tarefa> lista = new ArrayList<>();
        try {
            String sql = """
                    SELECT * FROM tarefa
                    WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setNome(rs.getString("nome"));
                tarefa.setData(rs.getString("data"));
                tarefa.setHora(rs.getString("hora"));
                tarefa.setTipoTarefa(rs.getString("tipoTarefa"));
                tarefa.setDisciplina(rs.getString("disciplina"));
                lista.add(tarefa);
            }
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
        return lista;
    }
}