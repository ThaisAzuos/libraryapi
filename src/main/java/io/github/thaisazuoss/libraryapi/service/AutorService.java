package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.controller.dto.response.AutorResponseDTO;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deletar(UUID idAutor) {
        autorRepository.deleteById(idAutor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){

        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null){
            return autorRepository.findByNome(nome);
        }
        if (nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();
    }
    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Não é possível atualizar um autor que não existe na base!");
        }
        autorRepository.save(autor);
    }
}
