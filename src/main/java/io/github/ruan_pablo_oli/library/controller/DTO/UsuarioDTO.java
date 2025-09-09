package io.github.ruan_pablo_oli.library.controller.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(@NotBlank(message = "Campo Obrigatório") String login,@NotBlank(message = "Campo obrigatório!") String senha, @NotBlank(message = "Campo obrigatório!") @Email(message = "Email inválido!") String email, List<String> roles) {
}
