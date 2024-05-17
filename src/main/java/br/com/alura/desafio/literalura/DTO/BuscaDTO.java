package br.com.alura.desafio.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BuscaDTO(int count,
                       String next,
                       String previous,
                       List<LivroDTO> results) {
}
