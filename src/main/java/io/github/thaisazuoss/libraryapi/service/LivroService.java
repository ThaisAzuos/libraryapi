package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.repository.LivroRepository;
import io.github.thaisazuoss.libraryapi.repository.specs.LivrosSpecs;
import io.github.thaisazuoss.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static io.github.thaisazuoss.libraryapi.repository.specs.LivrosSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {

        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarLivro(UUID idLivro) {

        return livroRepository.findById(idLivro);
    }

    public void deletar(Livro livroDeletado) {
        livroRepository.delete(livroDeletado);
    }

    public List<Livro> pesquisar(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao){

//        Specification<Livro> specs = Specification
//                .where(LivrosSpecs.isbnEqual(isbn)
//                        .and(LivrosSpecs.tituloLike(titulo))
//                        .and(LivrosSpecs.generoEqual(genero)));

        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Não é possível atualizar um livro que não existe na base!");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
