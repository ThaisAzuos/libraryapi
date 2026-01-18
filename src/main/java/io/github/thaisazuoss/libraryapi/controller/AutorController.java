package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.AutorRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.ErroResposta;
import io.github.thaisazuoss.libraryapi.controller.mappers.AutorMapper;
import io.github.thaisazuoss.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.thaisazuoss.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController {


    private final AutorService autorService;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid AutorRequestDTO autorRequestDTO){
        try {
            Autor autor = mapper.toEntity(autorRequestDTO);
            autorService.salvar(autor);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(uri).build();
        }catch (RegistroDuplicadoException e){
            var erroDto = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .buscarAutor(idAutor)
                .map(autor -> {
                   AutorResponseDTO autorResponseDTO = mapper.toDTO(autor);
                   return ResponseEntity.ok(autorResponseDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.buscarAutor(idAutor);
            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var autorDeletado = autor.get();
            autorService.deletar(autorDeletado);
            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitidaException e){
            var erroDto = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            @RequestParam(value  = "nome", required = false) String nome,
            @RequestParam(value  = "nacionalidade", required = false) String nacionalidade){
        List<Autor> listaPesquisa = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorResponseDTO> listaDto = listaPesquisa
                .stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(listaDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorRequestDTO autorRequestDTO){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.buscarAutor(idAutor);
            if ((autorOptional.isEmpty())) {
                return ResponseEntity.notFound().build();
            }
            var autor = autorOptional.get();
            autor.setNome(autorRequestDTO.nome());
            autor.setDataNascimento(autorRequestDTO.dataNascimento());
            autor.setNacionalidade(autorRequestDTO.nacionalidade());
            autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e){
            var erroDto = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
