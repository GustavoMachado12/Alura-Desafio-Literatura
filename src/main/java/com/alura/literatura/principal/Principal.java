package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.LivroRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConverteDados;
import com.alura.literatura.service.ServiceLivro;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner ler = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final ServiceLivro serviceLivro;
    private List<Livro> dadosLivro;

    private final String ENDERECO = "https://gutendex.com/books/";

    public Principal(ServiceLivro serviceLivro) {
        this.serviceLivro = serviceLivro;
    }

    public void mostraMenu(){
        var op = -1;

        while(op != 0){
            var menu = """
                    [1] Buscar Livro pelo Título
                    [2] Listar Livros Registrados
                    [3] Listar Autores Registrados
                    [4] Listar Autores Vivos em um Determinado Ano
                    [5] Listar Livros em um Determinado Idioma
                    
                    [0] Sair
                    """;

            System.out.println(menu);
            System.out.println("Digite uma opção: ");
            op = ler.nextInt();
            ler.nextLine();

            switch (op){
                case 1: //PESQUISA E CADASTRA ATRAVÉS DA API
                    pesquisaLivro();
                    break;
                case 2:
                    buscaTodosLivrosRegistrados();
                    break;
                case 3:
                    buscaTodosAutoresRegistrados();
                    break;
                case 4:
                    buscaAutorVivoEmDeterminadoAno();
                    break;
                case 5:
                    buscaLivroPorDeterminadoIdioma();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private void pesquisaLivro() {
        DadosLivro dadosLivro = getDadosLivro();
        Livro livro = new Livro(dadosLivro);

        var primeiroAutor = dadosLivro.authors().get(0);
        DadosAutor dadosAutor = new DadosAutor(primeiroAutor.name(), primeiroAutor.birth_year(), primeiroAutor.death_year());
        Autor autor = new Autor(dadosAutor);
        livro.setAuthor(autor);
        serviceLivro.salvaLivro(livro);
        System.out.println(livro);
    }

    private DadosLivro getDadosLivro(){
        System.out.println("Digite o Nome do Livro: ");
        var nomeLivro = ler.nextLine().toLowerCase();
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20"));

        DadosResultado dadosResultado = conversor.obterDados(json, DadosResultado.class);
        DadosLivro dadosLivro = dadosResultado.results().get(0);
        return dadosLivro;
    }

    private void buscaTodosLivrosRegistrados() {
        var livros = serviceLivro.getTodosLivros();
        if(!livros.isEmpty()){
            livros.forEach(System.out::println);
        } else {
            System.out.println("Nenhum livro registrado");
        }
    }

    private void buscaTodosAutoresRegistrados() {
        var autores = serviceLivro.getTodosAutores();
        if(!autores.isEmpty()){
            autores.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor registrado");
        }
    }


    private void buscaAutorVivoEmDeterminadoAno() {
        System.out.println("Digite o nome do Autor: ");
        String ano = ler.nextLine();

        var autorVivoEm = serviceLivro.getAuthorsAliveIn(ano);
        if(!autorVivoEm.isEmpty()){
            autorVivoEm.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor vivo está registrado nesse ano");
        }
    }

    private void buscaLivroPorDeterminadoIdioma() {
        var livrosPorIdioma = serviceLivro.getLivroPorIdioma();
        livrosPorIdioma.forEach(System.out::println);

    }
}
