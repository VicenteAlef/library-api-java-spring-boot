package com.library.api.service;

import com.library.api.model.Autor;
import com.library.api.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar( Autor autor){
        return autorRepository.save(autor);
    }
}
