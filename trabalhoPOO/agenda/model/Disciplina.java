package trabalhoPOO.agenda.model;

public class Disciplina {

    private int id;
    private String nome;

    public Disciplina() {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        Log.i("MainActivity", "pegou o id");
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        Log.i("MainActivity", "pegou o nome");
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}