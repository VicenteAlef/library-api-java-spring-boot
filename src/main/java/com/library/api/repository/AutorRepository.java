package com.library.api.repository;

import com.library.api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
