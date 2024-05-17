package br.com.alura.desafio.literalura.modelo;

import br.com.alura.desafio.literalura.DTO.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int birthYear;

    private int deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Livro> livro = new HashSet<>();

    public Set<Livro> getLivros() {
        return livro;
    }

    public void setLivros(Set<Livro> livro) {
        this.livro = livro;
    }

    public Autor(AutorDTO autorDTO) {
        this.name = autorDTO.name();
        this.birthYear = autorDTO.birth_year();
        this.deathYear = autorDTO.death_year();
    }

    public Autor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }





    public void adicionarLivro(Livro livro) {
        livro.setAutor(this);
        this.livro.add(livro);
    }
}
