package com.library.api.service;

import com.library.api.model.Autor;
import com.library.api.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar( Autor autor){
        return autorRepository.save(autor);
    }

    public void atualizar( Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Id não fornecido");
        }
        autorRepository.save(autor);
    }

    public Optional<Autor> buscarAutorPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void  deletarAutor(Autor autor){
        autorRepository.delete(autor);
    }

    public List<Autor> buscarAutoresPorNomeOuNacionalidade(String nome,  String nacionalidade){
         if (nome != null && nacionalidade != null){
             return autorRepository.findByNomeContainingIgnoreCaseAndNacionalidadeContainingIgnoreCase(nome,  nacionalidade);
         }

         if (nome != null){
             return autorRepository.findByNomeContainingIgnoreCase(nome);
         }
         if (nacionalidade != null){
             return autorRepository.findByNacionalidadeContainingIgnoreCase(nacionalidade);
         }

         return autorRepository.findAll();
    }
}
