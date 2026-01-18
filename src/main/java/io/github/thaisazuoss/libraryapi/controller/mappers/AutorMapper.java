package io.github.thaisazuoss.libraryapi.controller.mappers;

import ch.qos.logback.core.model.ComponentModel;
import io.github.thaisazuoss.libraryapi.controller.dto.request.AutorRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel =  "spring")
public interface AutorMapper {

    //exemplo de mapeamento quando o nome da propriedade do DTO
    //não é igual ao da entidade
    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorRequestDTO autorRequestDTO);

    AutorResponseDTO toDTO(Autor autor);

}
