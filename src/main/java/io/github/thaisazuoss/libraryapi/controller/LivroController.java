package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.LivroRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.ErroResposta;
import io.github.thaisazuoss.libraryapi.controller.dto.response.LivroResponseDTO;
import io.github.thaisazuoss.libraryapi.controller.mappers.LivroMapper;
import io.github.thaisazuoss.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("{id}")
    public ResponseEntity<LivroResponseDTO> obterDetalhes(@PathVariable("id") String id) {
        var idLivro = UUID.fromString(id);

        return livroService
                .buscarLivro(idLivro)
                .map(livro -> {
                    LivroResponseDTO livroResponseDTO = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(livroResponseDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id){

        var idLivro = UUID.fromString(id);

        return livroService
                .buscarLivro(idLivro)
                .map(livro -> {
                    livroService.deletar(livro);
                    return  ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
