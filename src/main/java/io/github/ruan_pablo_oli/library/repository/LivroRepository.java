package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    // Proura por um parâmetro ou outro e ordena pelo título
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo,String isbn);

     //Buscar entre intervalo de datas
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, Local fim);

    //Usando JPQL -> select l.* from livro as order by l.titulo
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodosOrdenadosPorTitulo();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();


    // Listar livros por títulos diferentes -> select distintic l.titulo from livro l

    @Query("select distinct l.titulo from Livro l")
    List<String> listarTitulosDiferentesLivros();


    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();


    //Named parameters
    @Query("select l from Livro l where l.genero = :genero order by :ordenarPor")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro,@Param("ordenarPor") String nomePropriedade);



    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParam(GeneroLivro generoLivro, String nomePropriedade);

    @Transactional
    @Modifying
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);


    @Transactional
    @Modifying
    @Query("update Livro set dataPublicacao = ?1 where titulo = ?2")
    void updateDataPublicacao(LocalDate novaData,String titulo);




}
