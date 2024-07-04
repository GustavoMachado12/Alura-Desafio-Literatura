package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("id") Long livroId,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DadosAutor> authors,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") Long download_Count
) {
}
