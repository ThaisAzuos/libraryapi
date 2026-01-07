package io.github.thaisazuoss.libraryapi.controller.dto.request;

import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        String isbn,

        @NotBlank(message = "campo obrigatório")
        String titulo,

        @NotNull(message = "campo obrigatório")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataPublicacao,

        GeneroLivro genero,
        BigDecimal preco,

        @NotNull(message = "campo obrigatório")
        UUID idAutor) {
}
