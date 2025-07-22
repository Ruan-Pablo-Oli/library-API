package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);


    // Procura por livros que contenham a string título ignorando se é maiúsculo ou minúsculo;
    List<Livro> findByTituloContainingIgnoreCase(String titulo);


    // Procura por mais de um parâmetro
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // Proura por um parâmetro ou outro
    List<Livro> findByTituloOrIsbn(String titulo,String isbn);
    

}
