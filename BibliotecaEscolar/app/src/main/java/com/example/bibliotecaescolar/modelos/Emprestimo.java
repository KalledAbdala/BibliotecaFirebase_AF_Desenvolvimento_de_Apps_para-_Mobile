package com.example.bibliotecaescolar.modelos;

public class Emprestimo {
    private String id;
    private String idAluno;
    private String nomeAluno;
    private String idLivro;
    private String nomeLivro;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo() {
    }

    // ✅ Construtor para compatibilidade com código antigo
    public Emprestimo(String id, String idLivro, String idAluno, String dataEmprestimo, String dataDevolucao) {
        this.id = id;
        this.idLivro = idLivro;
        this.idAluno = idAluno;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.nomeAluno = "";
        this.nomeLivro = "";
    }

    // ✅ Construtor completo
    public Emprestimo(String id, String idAluno, String nomeAluno, String idLivro, String nomeLivro, String dataEmprestimo, String dataDevolucao) {
        this.id = id;
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.idLivro = idLivro;
        this.nomeLivro = nomeLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    // Getters e setters...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
