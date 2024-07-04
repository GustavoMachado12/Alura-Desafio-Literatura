package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birth_year;
    private Integer death_year;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){ }

    public Autor(DadosAutor dadosAutor){
        String[] author = dadosAutor.name().split(",");
        this.name = author[1] + " " + author[0];
        this.birth_year = dadosAutor.birth_year();
        this.death_year = dadosAutor.death_year();
    }


    //GETTERS SETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        this.livros.add(livro);
        livro.setAuthor(this);
    }

    public List<String> todosLivros(){
        return livros.stream()
                .map(Livro::getTitle)
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return  "Nome: " + name + "\n" +
                "Nascimento: " + birth_year + "\n" +
                "Morte: '" + death_year + "\n";
    }
}

