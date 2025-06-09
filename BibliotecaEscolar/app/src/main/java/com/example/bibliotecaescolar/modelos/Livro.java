package com.example.bibliotecaescolar.modelos;

public class Livro {
    private String id;
    private String titulo;
    private String autor;
    private String genero;
    private String classificacao;
    private String anoPublicacao;

    public Livro() {}

    public Livro(String id, String titulo, String autor, String genero, String classificacao, String anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.classificacao = classificacao;
        this.anoPublicacao = anoPublicacao;
    }

    // getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getClassificacao() { return classificacao; }
    public void setClassificacao(String classificacao) { this.classificacao = classificacao; }

    public String getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; }
}
