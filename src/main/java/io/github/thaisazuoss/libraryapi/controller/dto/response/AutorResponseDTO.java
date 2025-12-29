package io.github.thaisazuoss.libraryapi.controller.dto.response;

import io.github.thaisazuoss.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade)
{

}
