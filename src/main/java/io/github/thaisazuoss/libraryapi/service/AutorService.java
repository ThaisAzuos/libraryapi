package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    AutorRepository autorRepository;

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }
}
