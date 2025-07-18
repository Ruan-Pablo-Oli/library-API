package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

}
