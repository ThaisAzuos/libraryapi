package io.github.thaisazuoss.libraryapi.controller.mappers;

import io.github.thaisazuoss.libraryapi.controller.dto.request.LivroRequestDTO;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(expression = "java( autorRepository.findById(livroRequestDTO.idAutor()).orElse(null) )", target = "autor")
    public abstract Livro toEntity(LivroRequestDTO livroRequestDTO);

}
