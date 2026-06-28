package com.library.api.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.library.api.model.Autor;
import com.library.api.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void saveTest() {
        Autor autor = new Autor();
        autor.setNome("J. R. R. Tolkien");
        autor.setNacionalidade("Inglesa");
        autor.setNataNascimento(LocalDate.of(1927, 7, 31));

        Autor autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo:" + autorSalvo);
    }

    @Test
    public void updateUser() {
        UUID uuid = UUID.fromString("c48756b2-f2e4-4d40-9e2a-203c0d3a04df");

        Optional<Autor> possivelAutor = autorRepository.findById(uuid);

        if (possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Ana Carla Silveira");

            autorRepository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTodos() {
        List<Autor> lista = autorRepository.findAll();

        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("\nContagem de autores: \n");
        System.out.println(autorRepository.count());
    }

    @Test
    public void listarLivrosAutor() {
        UUID id = UUID.fromString("9c286cb1-f9f1-4b2d-947c-e59d32e629b2");
        Autor autor = autorRepository.findById(id).get();

        List<Livro> livroList = livroRepository.findByAutor(autor);
        autor.setLivros(livroList);

        autor.getLivros().forEach(System.out::println);

    }

    @Test
    void listarAutor(){
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase("J.");
        autores.forEach(System.out::println);
    }
}
