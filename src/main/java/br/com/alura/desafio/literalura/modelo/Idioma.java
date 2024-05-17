package br.com.alura.desafio.literalura.modelo;

import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

public class Idioma {

    private String idioma;

    @ManyToMany(mappedBy = "languages")
    private Set<Livro> books = new HashSet<>();

    public Idioma(){}

    public Idioma(String name) {
        this.idioma = name;
    }


    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String name) {
        this.idioma = name;
    }

    public Set<Livro> getBooks() {
        return books;
    }

    public void setBooks(Set<Livro> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return  "\n, name=" + idioma + '\n' +
                ", books=" + books;
    }
}
