package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.LivroRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.ErroResposta;
import io.github.thaisazuoss.libraryapi.controller.dto.response.LivroResponseDTO;
import io.github.thaisazuoss.libraryapi.controller.mappers.LivroMapper;
import io.github.thaisazuoss.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> pesquisar(
            @RequestParam (value = "isbn", required = false)
            String isbn,
            @RequestParam (value = "titulo", required = false)
            String titulo,
            @RequestParam (value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam (value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam (value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina

    ){
        Page<Livro> paginaResultado = livroService.pesquisar(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<LivroResponseDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid LivroRequestDTO livroRequestDTO){

        var idLivro = UUID.fromString(id);
        return livroService
                .buscarLivro(idLivro)
                .map(livro -> {
                    Livro entidadeAux = livroMapper.toEntity(livroRequestDTO);

                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());
                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
