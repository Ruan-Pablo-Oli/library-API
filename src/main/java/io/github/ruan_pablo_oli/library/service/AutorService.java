package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.exceptions.OperacaoNaoPermitidaException;
import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import io.github.ruan_pablo_oli.library.repository.LivroRepository;
import io.github.ruan_pablo_oli.library.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Cria um construtor em tempo de execução  dos atributos que tem final
public class AutorService {


    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;


    public Autor salvar(Autor autor){

        autorValidator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletarPorId(Autor  autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor possui livros cadastrados!");
        }
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

    public List<Autor> pesquisaByExample(String nome,String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor,matcher);
        return autorRepository.findAll(autorExample);
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

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
