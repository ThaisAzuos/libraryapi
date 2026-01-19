package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
}
