package io.github.thaisazuoss.libraryapi.controller.dto.request;

import io.github.thaisazuoss.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorRequestDTO(
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
