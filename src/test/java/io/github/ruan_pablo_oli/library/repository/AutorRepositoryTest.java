package io.github.ruan_pablo_oli.library.repository;


import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository autorRepository;


    @Autowired
    LivroRepository livroRepository;


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

    // Salvar autor  com livros para o relacionamento oneToMany usando cascade
    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antônio");
        autor.setNacionalidade("Portugal");
        autor.setDataNascimento(LocalDate.of(1990,11,12));

        Livro livro = new Livro();
        livro.setIsbn("232-123");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Coração pulsante");
        livro.setDataPublicacao(LocalDate.of(2010, 11, 5));
        livro.setAutor(autor);

        autor.setLivros(new ArrayList<>());

        autor.getLivros().add(livro);

        Livro livro2 = new Livro();
        livro2.setIsbn("-8123123-123213");
        livro2.setPreco(BigDecimal.valueOf(150));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Coração  vibrante");
        livro2.setDataPublicacao(LocalDate.of(2012, 11, 5));
        livro2.setAutor(autor);

        autor.getLivros().add(livro2);

        autorRepository.save(autor);

        livroRepository.saveAll(autor.getLivros()); // Se o relacionamento de livros no autor fosse colocado em autor não precisaria dessa linha


    }



}
