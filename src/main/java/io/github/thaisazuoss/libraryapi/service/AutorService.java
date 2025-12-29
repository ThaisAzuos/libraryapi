package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    @Autowired
    AutorRepository autorRepository;

    public Autor salvar(Autor autor){

        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarAutor(UUID idAutor) {

        return autorRepository.findById(idAutor);
    }
}
