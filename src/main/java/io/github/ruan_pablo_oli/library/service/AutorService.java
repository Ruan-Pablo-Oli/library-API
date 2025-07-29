package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {


    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }


    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }
}
