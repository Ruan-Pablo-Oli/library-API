package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import io.github.ruan_pablo_oli.library.validator.AutorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;

    public AutorService(AutorRepository autorRepository,AutorValidator autorValidator){
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
    }


    public Autor salvar(Autor autor){

        autorValidator.validar(autor);
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

    public Autor atualizar(UUID id, AutorDTO autorDTO){

        Optional<Autor> autorOptional = obterPorId(id);
        if(autorOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var autor = autorOptional.get();
        autorValidator.validar(autor);
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setDataNascimento(autorDTO.dataNascimento());

        return autorRepository.save(autor);
    }
}
