package com.alura.literatura.service;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Livro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ServiceLivro {
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    LivroRepository livroRepository;
    public void salvaLivro(Livro livro){
        Livro livroSalvo = livroRepository.getByTitle(livro.getTitle());
        if(livroSalvo == null){
            Autor autor = livro.getAuthor();
            Autor autorSalvo = autorRepository.findByNameContainingIgnoreCase(autor.getName());
            if(autorSalvo != null){
                livro.setAuthor(autorSalvo);
                autorSalvo.setLivros(livro);
            } else {
                Autor autorGravado = autorRepository.save(autor);
                livro.setAuthor(autorGravado);
                autorGravado.setLivros(livro);
            }
            livroRepository.save(livro);
            System.out.println("Livro adicionado");
        } else {
            System.out.println("Esse livro já está cadastrado");
        }
    }

    public List<Livro> getTodosLivros(){
        return livroRepository.findAll();
    }

    public List<Autor> getTodosAutores(){
        return autorRepository.findAll();
    }

    public List<Autor> getAuthorsAliveIn(String year){
        return autorRepository.authorsAliveIn(year);
    }


    public List<Livro> getLivroPorIdioma() {
        Scanner ler = new Scanner(System.in);
        List<String> idiomas = livroRepository.languages();
        System.out.println("\n|Idiomas disponíveis|");
        if(idiomas.size() == 1){
            idiomas.forEach(System.out::println);
        } else {
        idiomas.forEach(i -> System.out.println(i + ", "));
        }
        System.out.println("\nDigite um idioma:");
        String idioma = ler.nextLine();
        if(!idiomas.contains(idioma)){
            System.out.println("Idioma não registrado");
        }
        return livroRepository.livrosByLanguage(idioma);
    }
}
