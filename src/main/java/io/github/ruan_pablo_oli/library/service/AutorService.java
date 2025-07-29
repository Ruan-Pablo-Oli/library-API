package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deletarPorId(Autor  autor) {
        autorRepository.delete(autor);

    }

    public List<Autor> pesquisa(String nome,String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeIgnoreCaseContainingAndNacionalidadeIgnoreCaseContaining(nome, nacionalidade);
        }

        if(nome != null){
            return autorRepository.findByNomeIgnoreCaseContaining(nome);
        }
        if(nacionalidade != null){
            return autorRepository.findByNacionalidadeIgnoreCaseContaining(nacionalidade);
        }

        return autorRepository.findAll();
    }
}
