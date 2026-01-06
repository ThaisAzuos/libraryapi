package io.github.thaisazuoss.libraryapi.repository;


import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class LivroRepositoryTest {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("9788594318602");
        livro.setPreco(BigDecimal.valueOf(14,50));
        livro.setTitulo("Dom Casmurro");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(1899, 1,30));

        var autor = autorRepository.findById(UUID.fromString("f4915aab-a912-4329-ac9a-8706a559592a")).orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("9788594318604");
        livro.setPreco(BigDecimal.valueOf(35,50));
        livro.setTitulo("O Alquimista");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(1935, 3,23));

        Autor autor = new Autor();
        autor.setNome("Paulo Coelho");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 4, 17));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }
    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("9788594318603");
        livro.setPreco(BigDecimal.valueOf(25,50));
        livro.setTitulo("A hora da estrela");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setDataPublicacao(LocalDate.of(1950, 5,12));

        Autor autor = new Autor();
        autor.setNome("Clarice Lispector");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1923, 9, 3));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivroTest(){

        UUID idLivro = UUID.fromString("04fbfe0e-d3fe-41ec-a4ce-e015d4d7464f");
        var livro = livroRepository.findById(idLivro).orElse(null);

        UUID idAutor = UUID.fromString("edc22e43-8443-42cf-b594-092d2e62982b");
        Autor autor = autorRepository.findById(idAutor).orElse(null);


            livro.setAutor(autor);
            livroRepository.save(livro);

    }
    @Test
    void deletarLivroTest(){

        UUID idLivro = UUID.fromString("04fbfe0e-d3fe-41ec-a4ce-e015d4d7464f");
        Livro livro = livroRepository.findById(idLivro).orElse(null);
        livroRepository.delete(livro);
    }

    @Test
    void pesquisarPorTituloTest(){

        String titulo = "Anjos e Demônios";
        List<Livro> livros = livroRepository.findByTitulo(titulo);

        livros.forEach(System.out::println);

    }

    @Test
    void pesquisarPorIsbnTest(){

        String isbn = "9788594318603";
        List<Livro> livros = livroRepository.findByIsbn(isbn);

        livros.forEach(System.out::println);

    }

    @Test
    void pesquisarPorTituloEPrecoTest(){
        String titulo = "Anjos e Demônios";
        BigDecimal preco = BigDecimal.valueOf(100);

        List<Livro> livros = livroRepository.findByTituloAndPreco(titulo, preco);
        livros.forEach(System.out::println);

    }

    @Test
    void pesquisarPorTituloOrIsbnTest(){
        String titulo = "Anjos e Demônios";
        String isbn = "9788594318603";

        List<Livro> livros = livroRepository.findByTituloOrIsbn(titulo, isbn);
        livros.forEach(System.out::println);

    }

    @Test
    void listarLivrosOrdenadosJPQLTest(){
        List<Livro> livros = livroRepository.buscarTodosOrdenadoPorTitulo();
        livros.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosJPQLTest(){
        List<Autor> autores = livroRepository.listarAutoresDosLivros();
        autores.forEach(System.out::println);
    }
    @Test
    void listarTitulosDeLivrosTest(){
        List<String> titulos = livroRepository.listarNomesDiferentesDeTitulos();
        titulos.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosTest(){
        List<String> generos = livroRepository.listarGenerosDeAutoresBrasileiros();
        generos.forEach(System.out::println);
    }
    @Test
    void listarLivrosComNamedParmTest(){
        List<Livro> livros = livroRepository.findByGenero(GeneroLivro.ROMANCE, "preco");
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComPositionalParmTest(){
        List<Livro> livros = livroRepository.findByGeneroPositionalParameters(GeneroLivro.ROMANCE, "preco");
        livros.forEach(System.out::println);
    }

    @Test
    void deletarPorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.MISTERIO);
    }

    @Test
    void atualizarPorDataTest(){
        livroRepository.atualizarDataPublicacao(LocalDate.of(2000, 01, 01));
    }

}