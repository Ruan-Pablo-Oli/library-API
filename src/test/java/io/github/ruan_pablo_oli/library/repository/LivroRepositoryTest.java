package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarLivroTeste(){
        Livro livro = new Livro();
        livro.setIsbn("989889-8990");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));



        Autor autor = autorRepository.findById(UUID.fromString("67b9c305-bd4d-4d76-b11b-342de610b0c7")).orElseThrow(() -> new RuntimeException("Erro ao buscar autor para livro!"));
        livro.setAutor(autor);


        livroRepository.save(livro);
    }


    //Salvando o livro com operação de persitência em cascada
    @Test
    void salvarLivroCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("123213-12312");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("A volta dos que não foram!");
        livro.setDataPublicacao(LocalDate.of(1999,12,5));

        Autor autor = new Autor();
        autor.setNome("Ruan");
        autor.setDataNascimento(LocalDate.of(1960,5,25));
        autor.setNacionalidade("Alemão");


        livro.setAutor(autor);


        livroRepository.save(livro);
    }

}