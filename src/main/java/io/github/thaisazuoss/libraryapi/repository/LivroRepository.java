package io.github.thaisazuoss.libraryapi.repository;

import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.model.GeneroLivro;
import io.github.thaisazuoss.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {



    //Query Method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // JPQL -> referencia as entidades e as propriedades das entidades e não do banco de dados

    // select l.* from livro as l order by l.titulo
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> buscarTodosOrdenadoPorTitulo();
    /*
      select a.*
      from livro l
      join autor a on a.id = l.id_autor
    */
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    //select distinct titulo from livro;
    @Query("select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesDeTitulos();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGenerosDeAutoresBrasileiros();

    //named parametrs --> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomeDaPropriedade
    );

    //positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomeDaPropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void atualizarDataPublicacao(LocalDate data);

    boolean existsByAutor(Autor autor);
}
