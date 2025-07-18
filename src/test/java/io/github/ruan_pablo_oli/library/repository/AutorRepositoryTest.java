package io.github.ruan_pablo_oli.library.repository;


import io.github.ruan_pablo_oli.library.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    @Test
    public void atualizarAutor(){
        var id = UUID.fromString("9d4a4475-558a-4f0a-8b43-ff5bcd50d628");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);
            autorEncontrado.setDataNascimento(LocalDate.of(1952,1,30));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarAutor(){
        List<Autor> lista = autorRepository.findAll();

        for(Autor autor : lista){
            System.out.println(autor);
        }
    }


    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("9d4a4475-558a-4f0a-8b43-ff5bcd50d628");

        autorRepository.deleteById(id);
    }


    @Test
    public void deleteTest(){
        var id = UUID.fromString("9d4a4475-558a-4f0a-8b43-ff5bcd50d628");
        var autor =  autorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado!"));
        autorRepository.delete(autor);
    }



}
