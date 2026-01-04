package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import io.github.thaisazuoss.libraryapi.repository.LivroRepository;
import io.github.thaisazuoss.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }


    public Autor salvar(Autor autor){
        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarAutor(UUID idAutor) {

        return autorRepository.findById(idAutor);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
           throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui livros cadastrados !");
        }
        autorRepository.delete(autor);
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
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
