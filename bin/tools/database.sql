CREATE DATABASE agenda;

USE agenda;

CREATE TABLE disciplina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    professor VARCHAR(50) NOT NULL);

CREATE TABLE tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data VARCHAR(15) NOT NULL,
    hora CHAR(8) NOT NULL,
    tipoTarefa VARCHAR(20),
    disciplina VARCHAR(50) NOT NULL);
