package com.library.api.repository;

import com.library.api.model.Autor;
import com.library.api.model.GeneroLivro;
import com.library.api.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see  LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_author = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = ?
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or preco = ?
    List<Livro> findByTituloOrPreco(String titulo, BigDecimal preco);

    // JPQL -> referencia as entidades e propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarLivros();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGeneros();

    // Named params
    @Query("select l from Livro l where l.genero = :genero order by :paramOrder")
    List<Livro> findByGenero(
            @Param("genero")GeneroLivro generoLivro,
            @Param("paramOrder") String paramOrder
    );

    // Positional param
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositional(GeneroLivro generoLivro, String paramOrder);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);
}
