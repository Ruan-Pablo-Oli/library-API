package io.github.ruan_pablo_oli.library.controller.DTO;

import io.github.ruan_pablo_oli.library.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,String nome, LocalDate dataNascimento, String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
