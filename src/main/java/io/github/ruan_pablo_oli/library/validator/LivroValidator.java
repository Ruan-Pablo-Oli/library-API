package io.github.ruan_pablo_oli.library.validator;


import io.github.ruan_pablo_oli.library.exceptions.registroDuplicadoException;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository;


    public void validar(Livro livro){

        if(existeLivroComIsbn(livro)){
            throw  new registroDuplicadoException("ISBN já cadastrado!");
        }
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
