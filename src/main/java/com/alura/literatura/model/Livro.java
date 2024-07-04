package com.alura.literatura.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String authorName;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Autor author;
    private String language;
    private Long download_Count;


    public Livro(){ }

    public Livro(DadosLivro dadosLivro){
        this.title = dadosLivro.title();
        this.authorName = dadosLivro.authors().get(0).name();
        this.language = languageModificade(dadosLivro.language());
        this.download_Count = dadosLivro.download_Count();
    }

    private String languageModificade(List<String> language){
        if(language == null || language.isEmpty()){
            return "Desconhecido";
        }
        return language.get(0);
    }

    //GETTERS SETTERS
    public String getTitle() {
        return title;
    }

    public Autor getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public Long getDownload_Count() {
        return download_Count;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthor(Autor author) {
        this.author = author;
    }



    @Override
    public String toString() {
        return "Título: " + title +
                "\nAutor: " + author.getName() +
                "\nIdioma: " + language +
                "\nDownloads: " + download_Count + "\n";
    }
}
