package io.github.ruan_pablo_oli.library.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
@Data // Anotação do lombock que implementa o @Getter para criar getters @Setter para criar setters, @ToString para criar o to string, @EqualsAndHashCode para criar equals and hash code e também o @RequiredArgsConstructor para criar o construtor de atributos Final
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn",nullable = false,length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false,length = 150)
    private String titulo;

    @Column(name = "data_publicacao",nullable = false)
    private LocalDate dataPublicacao;


    @Enumerated(EnumType.STRING)
    @Column(name="genero",nullable = false,length = 30)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;


}
