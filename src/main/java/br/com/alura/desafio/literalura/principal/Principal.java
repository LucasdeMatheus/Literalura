package br.com.alura.desafio.literalura.principal;

import br.com.alura.desafio.literalura.DTO.AutorDTO;
import br.com.alura.desafio.literalura.DTO.BuscaDTO;
import br.com.alura.desafio.literalura.DTO.LivroDTO;
import br.com.alura.desafio.literalura.modelo.Autor;
import br.com.alura.desafio.literalura.modelo.Livro;
import br.com.alura.desafio.literalura.repositorio.AutorRepositorio;
import br.com.alura.desafio.literalura.serviço.ConsumoApi;
import br.com.alura.desafio.literalura.serviço.ConverteDados;

import java.util.*;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converter = new ConverteDados();

    private AutorRepositorio autorRepositorio;

    public Principal(AutorRepositorio autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
    }
    
    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                                    Bem vindo ao Literalura
                    menu
                    ------------------------------------------------------------
                    (escolha a opção pelo numero)
                    1- buscar livros
                    2- buscar livros registrados
                    3- autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    
                    0- sair
                    
                    """;
            System.out.println("------------------------------------------------------------\n\n");


            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    buscarLivrosRegistrados();
                    break;
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("digite o numero valido");
            }

        }
    }




    private void buscarLivro() {
        System.out.println("digite o nome do autor ou o titulo: ");
        var titulo = sc.nextLine();
        var json = consumoApi.obterDados(ENDERECO + "?search=" + titulo.replace(" ", "%20"));
        BuscaDTO buscaDTO = new ConverteDados().obterDados(json, BuscaDTO.class);
        List<LivroDTO> livros = new ArrayList<>();
        List<AutorDTO> dadosAutor = new ArrayList<>();
        List<String> idiomas = new ArrayList<>();
        if (buscaDTO.count() != 0) {
            for (int i = 0; i < buscaDTO.results().size(); i++) {
                livros.add(buscaDTO.results().get(i));
            }
//        livros.forEach(l -> System.out.println("Titulo: " + l.title()));

            for (LivroDTO livro : livros) {
                dadosAutor.addAll(livro.authors());
            }
//        dadosAutor.forEach(a -> System.out.println("Autor: " + a.name()));

            for (LivroDTO livro : livros) {
                idiomas.addAll(livro.languages());
            }
//        idiomas.forEach(i -> System.out.println("Idioma: " + i));

            for (int i = 0; i < buscaDTO.results().size(); i++) {
                var livro = livros.get(i);
                System.out.println("""
                        ------------------------------------------------------------
                                                  
                                                  
                                                  Livro %d
                        ------------------------------------------------------------""".formatted((i + 1)));
                System.out.println("Titulo: " + livro.title() + "\nIdioma: " + livro.languages().get(0) + "\nAutor(es): " + "\n\nNome: " + livro.authors().get(0).name() + "\nData de nascimento: " + livro.authors().get(0).birth_year() + "\nFalecido: " + livro.authors().get(0).death_year());
            }


            System.out.println("\nQual livro deseja adicionar ao seu banco de dados?\nDigite o número do livro");

            var escolha = sc.nextInt();
            sc.nextLine();
            Autor autor = new Autor(dadosAutor.get((escolha - 1)));
            Livro livro = new Livro(livros.get(escolha - 1));


            Optional<Autor> encontrarAutor = autorRepositorio.findByTitulo(autor.getName());
            if (encontrarAutor.isPresent()){
                var encontradoAutor = encontrarAutor.get();
                encontradoAutor.adicionarLivro(livro);

                System.out.println("\no Autor ja esta presente no banco de dados, iremos adicionar apenas o livro.");

                autorRepositorio.save(encontradoAutor);
                System.out.println("\nlivro salvo com sucesso!");
            }else {
                autor.adicionarLivro(livro);

                System.out.println("\nautor e livro salvo com sucesso!");
                autorRepositorio.save(autor);
            }

            System.out.println("------------------------------------------------------------\n");
        }else {
            System.out.println("Livro ou autor não encontrado");
        }
    }

    private void buscarLivrosRegistrados() {
        List<Autor> autor = autorRepositorio.findAll();
        autor.stream().sorted(Comparator.comparing(Autor::getName))
                .forEach(a -> {
                    System.out.println("""
                        ------------------------------------------------------------
                        Autor: %s 
                        Titulos:
                        """.formatted(a.getName()));
                    a.getLivros().forEach(l -> {
                        System.out.println("""
                            id: %d
                            Nome: %s
                            Idioma: %s
                            
                            """.formatted(l.getId(), l.getTitulo(), l.getIdioma()));
                    });
                });
        System.out.println("------------------------------------------------------------\n\n");
    }

    private void buscarAutoresRegistrados() {
        List<Autor> autor = autorRepositorio.findAll();
        System.out.println("""
                ------------------------------------------------------------
                Autores cadastrados:
                """);
        autor.stream().sorted(Comparator.comparing(Autor::getId))
                .forEach(a -> System.out.println("\nid: " + a.getId() + "\nAutor: " + a.getName()));
        System.out.println("------------------------------------------------------------\n\n");
    }

    private void listarAutoresVivos() {

        System.out.println("digite até que ano os autores viveram deseja consultar:");
        int ano = sc.nextInt();
        List<Autor> autor = autorRepositorio.findByAuthorsAlive(ano);System.out.println("""
                ------------------------------------------------------------
                Autores vivos até %d
                """.formatted(ano));
        autor.stream()
                .sorted(Comparator.comparing(Autor::getBirthYear))
                .forEach(a -> System.out.println("""
                    id: %d
                    Nome: %s
                    Nascido: %d
                    falecido: %d
                    """.formatted(a.getId(), a.getName(), a.getBirthYear(), a.getDeathYear())));

    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite a sigla do idioma que deseja consultar: \ninglês: en\nportuguês: pt");
        var idioma = "[" + sc.nextLine() + "]";
        System.out.println("------------------------------------------------------------\n");

        List<Autor> autor = autorRepositorio.findAll();
        autor.stream().forEach(a -> a.getLivros().forEach(l -> {
            if (l.getIdioma().equals(idioma)) {
                System.out.println("""
                        ------------------------------------------------------------
                        
                        Autor: %s
                        id: %d
                        Titulo: %s
                        Idioma: %s
                        
                        """.formatted(a.getName(), l.getId(), l.getTitulo(), l.getIdioma()));
            }
        })
        );
    }
}
