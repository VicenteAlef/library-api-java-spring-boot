package com.library.api.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.library.api.model.Autor;
import com.library.api.model.GeneroLivro;
import com.library.api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("15672-665482");
        livro.setPreco(BigDecimal.valueOf(89));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Harry Potter e a Pedra Filosofal");
        livro.setDataPublicacao(LocalDate.of(1954, 7, 29));

        Autor autor = autorRepository.findById(UUID.fromString("fe33f218-d2bb-4891-96ee-ed3d5127f6c3")).orElse(null);
        livro.setAutor(autor);
        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("45122-12346");
        livro.setPreco(BigDecimal.valueOf(1000));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("O Viajante");
        livro.setDataPublicacao(LocalDate.of(1995, 5, 12));

        Autor autor = new Autor();
        autor.setNome("José Riveiro");
        autor.setNacionalidade("Brasileira");
        autor.setNataNascimento(LocalDate.of(1987, 2, 12));
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void buscaLivrosTest() {
        List<Livro> listaLivros = repository.findAll();
        listaLivros.forEach(System.out::println);
    }

    @Test
    void bucarLivroTest() {
        UUID id = UUID.fromString("9c286cb1-f9f1-4b2d-947c-e59d32e629b2");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        // System.out.println("Autor: ");
        // System.out.println(livro.getAutor().getNome());
    }

    @Test
    void listarLivrosComJPQL() {
        List<Livro> livroList = repository.listarLivros();
        livroList.forEach(System.out::println);
    }

    @Test
    void listarAutorLivrosJPQL() {
        List<Autor> autorList = repository.listarAutoresLivros();
        autorList.forEach(System.out::println);
    }

    @Test
    void listarNomesLivros() {
        List<String> nomesDiferentesLivrossList = repository.listarNomesDiferentesLivros();
        nomesDiferentesLivrossList.forEach(System.out::println);
    }

    @Test
    void listarLivroPorGenero() {
        List<Livro> preco = repository.findByGenero(GeneroLivro.FANTASIA, "preco");
        preco.forEach(System.out::println);

    }

    @Test
    void deletarLivroPorGenero() {
        repository.deleteByGenero(GeneroLivro.BIOGRAFIA);
    }

}