package com.example.bibliotecaescolar.modelos;

public class Aluno {
    private String id;
    private String nome;
    private String matricula;
    private String turma;

    // Construtor vazio obrigatório para Firebase
    public Aluno() {
    }

    public Aluno(String id, String nome, String matricula, String turma) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.turma = turma;
    }

    // Getters e Setters obrigatórios para Firebase

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
