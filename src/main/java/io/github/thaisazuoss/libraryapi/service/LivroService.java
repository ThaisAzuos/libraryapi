package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.repository.LivroRepository;
import io.github.thaisazuoss.libraryapi.repository.specs.LivrosSpecs;
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

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarLivro(UUID idLivro) {
        return livroRepository.findById(idLivro);
    }

    public void deletar(Livro livroDeletado) {
        livroRepository.delete(livroDeletado);
    }

    public List<Livro> pesquisar(String isbn, String titulo, String nomeAutor, GeneroLivro genero, int anoPublicacao){

//        Specification<Livro> specs = Specification
//                .where(LivrosSpecs.isbnEqual(isbn)
//                        .and(LivrosSpecs.tituloLike(titulo))
//                        .and(LivrosSpecs.generoEqual(genero)));

        Specification<Livro> specs = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        return livroRepository.findAll(specs);
    }
}
