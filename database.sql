CREATE DATABASE agenda;

USE agenda;

CREATE TABLE disciplinas (
    id INT NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL);

CREATE TABLE tarefa (
    id INT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data CHAR(10) NOT NULL,
    hora CHAR(8) NOT NULL,
    tipoTarefa VARCHAR(20),
    idDisciplina INT NOT NULL,
    FOREIGN KEY (idDisciplina) REFERENCES disciplina(id));

-- INSERT INTO contatos (nome, email, telefone)
-- VALUES ('Joao Silva', 'joao@teste.com', '(11)1111-1111')