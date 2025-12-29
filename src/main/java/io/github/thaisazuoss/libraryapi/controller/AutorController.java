package io.github.thaisazuoss.libraryapi.controller;

import io.github.thaisazuoss.libraryapi.controller.dto.AutorDTO;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    public AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autorDTO){
        Autor autor = autorDTO.mapearParaAutor();
        autorService.salvar(autor);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
