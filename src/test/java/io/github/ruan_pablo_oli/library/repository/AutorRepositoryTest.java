package io.github.ruan_pablo_oli.library.repository;


import io.github.ruan_pablo_oli.library.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository autorRepository;


    @Test
    public void salvarTeste(){
        Autor newAutor = new Autor();
        newAutor.setNome("Maria");
        newAutor.setDataNascimento(LocalDate.of(1960,5,25));
        newAutor.setNacionalidade("Irlandesa");

        var autorSalvo = autorRepository.save(newAutor);
    }
}
