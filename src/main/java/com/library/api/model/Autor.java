package com.library.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"livros"})
public class Autor {

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate nataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL) // mapeado pela propriedade autor em Livros
    //@Transient
    private List<Livro> livros;
}
