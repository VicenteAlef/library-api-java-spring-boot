package com.library.api.controller;

import com.library.api.dto.AutorDTO;
import com.library.api.model.Autor;
import com.library.api.repository.AutorRepository;
import com.library.api.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController {


    private AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        Autor autorEntity = autor.mapearParaAutor();
        autorService.salvar(autorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> buscarAutorPorId(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarAutorPorId(idAutor);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO  dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getNataNascimento(),
                    autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAutorPorId(@PathVariable String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarAutorPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletarAutor(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarAutores(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> listAutor = autorService.buscarAutoresPorNomeOuNacionalidade(nome, nacionalidade);
        List<AutorDTO> list = listAutor
                .stream()
                .map( autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getNataNascimento(),
                        autor.getNacionalidade()
                )).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> editarAutor(@PathVariable("id") String id, @RequestBody AutorDTO autor) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.buscarAutorPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autorEntity = autor.mapearParaAutor();
        autorEntity.setId(idAutor);
        autorService.atualizar(autorEntity);

        return ResponseEntity.noContent().build();
    }
}
