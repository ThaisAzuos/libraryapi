package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.request.AutorRequestDTO;
import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    public AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorRequestDTO autorRequestDTO){
        Autor autor = autorRequestDTO.mapearParaAutor();
        autorService.salvar(autor);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obtarDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.buscarAutor(idAutor);
        if (autor.isPresent()) {
            AutorResponseDTO autorResponseDTO = new AutorResponseDTO(
                    autor.get().getId(),
                    autor.get().getNome(),
                    autor.get().getDataNascimento(),
                    autor.get().getNacionalidade());

            return ResponseEntity.ok(autorResponseDTO);
        }
        else {
            return ResponseEntity.notFound().build();

        }
    }
}
