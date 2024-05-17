package br.com.alura.desafio.literalura.repositorio;

import br.com.alura.desafio.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.name = :name")
    Optional<Autor> findByTitulo(String name);

    @Query("SELECT a FROM Autor a WHERE a.deathYear <= :ano")
    List<Autor> findByAuthorsAlive(int ano);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.idioma) = LOWER(:idioma)")
    List<Autor> findAllByIdioma(String idioma);
}
