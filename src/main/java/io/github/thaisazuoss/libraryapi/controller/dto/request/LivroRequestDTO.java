package io.github.thaisazuoss.libraryapi.controller.dto.request;

import io.github.thaisazuoss.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDTO(
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        UUID idAutor) {
}
