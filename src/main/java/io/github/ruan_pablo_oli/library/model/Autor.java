package io.github.ruan_pablo_oli.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="autor", schema = "public")
@Getter
@Setter
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column( name = "nome", nullable = false, length = 100)
    private String nome;

    @Column( name = "data_nascimento",nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade",nullable = false,length = 50)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;



}
