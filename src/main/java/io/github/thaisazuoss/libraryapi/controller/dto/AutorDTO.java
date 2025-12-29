package io.github.thaisazuoss.libraryapi.controller.dto;

import io.github.thaisazuoss.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }
}
