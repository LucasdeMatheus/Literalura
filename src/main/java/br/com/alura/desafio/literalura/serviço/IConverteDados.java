package br.com.alura.desafio.literalura.serviço;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
