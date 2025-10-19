package io.github.ruan_pablo_oli.library.controller.DTO;

import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "CadastroLivro")
public record CadastroLivroDTO(@ISBN @NotBlank(message = "campo obrigatório!") String isbn,
                               @NotBlank(message = "campo obrigatório!")String titulo,
                               @Past(message = "não pode ser uma data futura") @NotNull(message = "campo obrigatório!") LocalDate dataPublicacao,
                               GeneroLivro genero,
                               BigDecimal preco,
                               @NotNull(message = "campo obrigatório!") UUID idAutor
                            ) {
}
