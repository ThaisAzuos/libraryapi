package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.LivroRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.ErroResposta;
import io.github.thaisazuoss.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.thaisazuoss.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid LivroRequestDTO livroRequestDTO){
        try {
            //MAPEAR DTO PARA ENTIDADE

            //ENVIAR A ENTIDADE PARA O SERVICE VALIDAR E SALVAR NA BASE

            //CRIAR URL PASRA ACESSO DOS DADOS DO LIVRO

            //RETORNAR CODIGO CREATED COM HEADER LOCATION

            return ResponseEntity.ok(livroRequestDTO);
        }catch (RegistroDuplicadoException e){
            var erroDto = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
