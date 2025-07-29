package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {


    List<Autor> findByNomeIgnoreCaseContaining(String nome);
    List<Autor> findByNacionalidadeIgnoreCaseContaining(String nacionalidade);
    List<Autor> findByNomeIgnoreCaseContainingAndNacionalidadeIgnoreCaseContaining(String nome, String nacionalidade);
}
