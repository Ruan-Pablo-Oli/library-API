package io.github.ruan_pablo_oli.library.controller.DTO;

import io.github.ruan_pablo_oli.library.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

//@NotBlank ->annotation do validation-starter pack para garaintir que uma string não seja vazia
// @NotNull -> annotation para garantir que outros campos que não seja strings não estejam null

public record AutorDTO(UUID id, @NotBlank(message = "campo obrigatório") String nome, @NotNull(message = "campo obrigatório") LocalDate dataNascimento, @NotBlank(message = "campo obrigatório")  String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
