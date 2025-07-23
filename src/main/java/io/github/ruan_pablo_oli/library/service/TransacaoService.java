package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import io.github.ruan_pablo_oli.library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    @Transactional
    public void atualicavaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("a769e1df-7c24-4e16-81a5-09d0ce45908c")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024,6,1));

        // Desnecessário pois uma janela de transaçaõ já está aberta
        livroRepository.save(livro);

    }

    @Transactional
    public void executar(){
        // Salva o autor
        Autor autor = new Autor();
        autor.setNome("Francisca");
        autor.setDataNascimento(LocalDate.of(1930, 5, 25));
        autor.setNacionalidade("Brasileira");



        // Salva o livro
        Livro livro = new Livro();
        livro.setIsbn("123-434");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Livro da francisca.");
        livro.setDataPublicacao(LocalDate.of(1960, 6, 6));




        livro.setAutor(autor);


        livroRepository.save(livro);

        if(autor.getNome().equals("Francisca")){
            throw new RuntimeException("Rollback!");
        }
    }
}
