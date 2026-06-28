package com.library.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data // Notation Lombok = @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@ToString(exclude = "autor")
public class Livro {

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) // Necessario quando utilizando ENUM
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    //private Double preco;
    private BigDecimal preco;

    @ManyToOne(
            // cascade = CascadeType.ALL
            // fetch = FetchType.EAGER // Padraão quando a relação é ManyToOne
            fetch = FetchType.LAZY // Para trazer somente os dados da entidade selecionada (sem entidade relacionada)
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
