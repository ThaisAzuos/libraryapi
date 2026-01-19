package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.LivroRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.ErroResposta;
import io.github.thaisazuoss.libraryapi.controller.mappers.LivroMapper;
import io.github.thaisazuoss.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid LivroRequestDTO livroRequestDTO){

        //MAPEAR DTO PARA ENTIDADE
        Livro livro = livroMapper.toEntity(livroRequestDTO);

        //ENVIAR A ENTIDADE PARA O SERVICE VALIDAR E SALVAR NA BASE
        livroService.salvar(livro);

        //CRIAR URL PARA ACESSO DOS DADOS DO LIVRO
        URI uri = gerarHeaderLocation(livro.getId());

        //RETORNAR CODIGO CREATED COM HEADER LOCATION
        return ResponseEntity.created(uri).build();

    }
}
