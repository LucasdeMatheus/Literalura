package br.com.alura.desafio.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO (
        int id,
        String title,
        List<AutorDTO>authors,
        List<String> languages
){
}
