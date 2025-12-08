package io.github.thaisazuoss.libraryapi.service;

import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import io.github.thaisazuoss.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){

        var livro = livroRepository
                .findById(UUID.fromString("06e4435c-6b7a-4e66-91df-a28a39fdf241"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 2, 18));
        //se eu estou com a transação aberta, eu não preciso chamar o save da interface livroRepository
        //livroRepository.save(livro);
    }

    @Transactional
    public void executar(){

        //Salva o autor:

        Autor autor = new Autor();
        autor.setNome("Paulo Coelho");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 4, 17));

        autorRepository.save(autor);

        //Salva o livro:

        Livro livro = new Livro();
        livro.setIsbn("9788594318604");
        livro.setPreco(BigDecimal.valueOf(35,50));
        livro.setTitulo("O Alquimista");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(1935, 3,23));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("José")) {
            throw new RuntimeException("Rollback!");
        }
    }

}
