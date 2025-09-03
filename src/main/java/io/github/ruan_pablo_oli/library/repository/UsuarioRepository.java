package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByLogin(String login);

}
