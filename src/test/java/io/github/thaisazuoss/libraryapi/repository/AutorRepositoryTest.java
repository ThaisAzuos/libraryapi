package io.github.thaisazuoss.libraryapi.repository;

import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;


    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1839, 6, 21));
        var autorSalvo = autorRepository.save(autor);
        System.out.println(autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("704304d7-d542-4c11-b988-f233f4d9aead");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: " + possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1994, 10, 12));
            autorEncontrado.setNome("Thaís Souza");

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void contarTest(){
        var totalAutores = autorRepository.count();
        System.out.println("A quantidades de autores registrados na base é: " + totalAutores);
    }

    @Test
    public void deletarPorIdTest(){
        var id = UUID.fromString("704304d7-d542-4c11-b988-f233f4d9aead");
        autorRepository.deleteById(id);
    }

    @Test
    public void deletarTest(){

        var id = UUID.fromString("7b4ab2c0-73cd-4744-8687-cd676710ca84");
        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()){
            var autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor a ser deletado: " + possivelAutor.get());
            autorRepository.delete(autorEncontrado);
        }
    }

    @Test
    public void salvarAutoComLivrosTest(){

        Autor autor = new Autor();
        autor.setNome("Dan Brown");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1964, 6, 22));

        Livro livro1 = new Livro();
        livro1.setIsbn("97885943186055");
        livro1.setPreco(BigDecimal.valueOf(85));
        livro1.setTitulo("O código Da Vinci");
        livro1.setGenero(GeneroLivro.ROMANCE);
        livro1.setDataPublicacao(LocalDate.of(2006, 3,1));
        livro1.setAutor(autor);


        Livro livro2 = new Livro();
        livro2.setIsbn("9788594318616");
        livro2.setPreco(BigDecimal.valueOf(100));
        livro2.setTitulo("Anjos e Demônios");
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setDataPublicacao(LocalDate.of(2000, 5,12));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro1);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);

    }

    @Test
    //@Transactional
    public void listarLivrosAutorTest(){
        var id_autor = UUID.fromString("fe8122cd-995a-484c-a179-e26ada96be22");
        var autor = autorRepository.findById(id_autor).get();

        //buscar os livros do autor
        List<Livro> livros = livroRepository.findByAutor(autor);
        //inclui a lista de livros retornadas pela consulta na classe autor;
        autor.setLivros(livros);
        //Percorre a lista de livros do autor
        autor.getLivros().forEach(System.out::println);
    }

}