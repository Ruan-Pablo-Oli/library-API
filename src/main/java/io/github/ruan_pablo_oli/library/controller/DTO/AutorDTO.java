package io.github.ruan_pablo_oli.library.controller.DTO;

import io.github.ruan_pablo_oli.library.model.Autor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

//@NotBlank ->annotation do validation-starter pack para garaintir que uma string não seja vazia
// @NotNull -> annotation para garantir que outros campos que não seja strings não estejam null

public record AutorDTO(UUID id, @NotBlank(message = "campo obrigatório") @Size(min = 2,max = 100,message = "campo fora do tamanho padrão") String nome, @Past(message = "Não pode ser uma data futura!") @NotNull(message = "campo obrigatório") LocalDate dataNascimento, @NotBlank(message = "campo obrigatório") @Size(min = 2,max =50,message = "campo fora do tamanho padrão") String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
