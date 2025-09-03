package io.github.ruan_pablo_oli.library.controller.DTO;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
