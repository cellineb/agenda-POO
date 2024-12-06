package persistence;

import model.Disciplina;
import tools.AgendaException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAOImpl implements IDisciplinaDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/agenda";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public DisciplinaDAOImpl() throws AgendaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new AgendaException(e);
        }
    }

    @Override
    public void inserir(Disciplina d) throws AgendaException {
        try {
            String sql = """
                    INSERT INTO disciplina (id, nome, professor)
                    VALUES (?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, d.getId());
            stm.setString(2, d.getNome());
            stm.setString(3, d.getProfessor());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public void atualizar(Disciplina d) throws AgendaException {
        try {
            String sql = """
                    UPDATE disciplina SET id=?, nome=?, professor=?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, d.getId());
            stm.setString(2, d.getNome());
            stm.setString(3, d.getProfessor());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public void remover(Disciplina d) throws AgendaException {
        try {
            String sql = """
                    DELETE FROM disciplina
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, d.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
    }

    @Override
    public List<Disciplina> pesquisarPorNome(String nome) throws AgendaException {
        List<Disciplina> lista = new ArrayList<>();
        try {
            String sql = """
                    SELECT * FROM disciplina
                    WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setProfessor(rs.getString("professor"));
                lista.add(disciplina);
            }
        } catch (SQLException err) {
            throw new AgendaException(err);
        }
        return lista;
    }
}