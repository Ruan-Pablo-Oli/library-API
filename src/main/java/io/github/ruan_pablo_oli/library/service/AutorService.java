package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {


    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }


    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }
}
