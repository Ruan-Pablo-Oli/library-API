package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;


    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}
