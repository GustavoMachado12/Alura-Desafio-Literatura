package com.alura.literatura.repository;

import com.alura.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {


    Livro getByTitle(String title);

    @Query("SELECT DISTINCT l.language FROM Livro l ORDER BY l.language")
    List<String> languages();

    @Query("SELECT l FROM Livro l WHERE language = :idioma")
    List<Livro> livrosByLanguage(String idioma);
}
