package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import jakarta.transaction.TransactionScoped;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * @see LivroRepositoryTest
 */
@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
     void salvarLivroTeste() {
        Livro livro = new Livro();
        livro.setIsbn("123-434");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Biografia de alguém");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));


        //Autor autor = autorRepository.findById(UUID.fromString("67b9c305-bd4d-4d76-b11b-342de610b0c7")).orElseThrow(() -> new RuntimeException("Erro ao buscar autor para livro!"));
        //livro.setAutor(autor);


        livroRepository.save(livro);
    }


    //Salvando o livro com operação de persitência em cascada
    @Test
    void salvarLivroCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("123123-434");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Casa dos mortos.");
        livro.setDataPublicacao(LocalDate.of(1960, 6, 6));

        Autor autor = new Autor();
        autor.setNome("Rodrigo");
        autor.setDataNascimento(LocalDate.of(1930, 5, 25));
        autor.setNacionalidade("Brasileira");


        livro.setAutor(autor);


        livroRepository.save(livro);
    }


    @Test
    public void atualizarAutorLivro() {
        var livroParaAtualizar = livroRepository.findById(UUID.fromString("a769e1df-7c24-4e16-81a5-09d0ce45908c")).orElse(null);

        var novoAutor = autorRepository.findById(UUID.fromString("67b9c305-bd4d-4d76-b11b-342de610b0c7")).orElse(null);

        livroParaAtualizar.setAutor(novoAutor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    public void deletarporId() {
        var id = UUID.fromString("b565ecbc-d70e-4b6f-b5c5-0ab9f46c16e3");
        livroRepository.deleteById(id);

    }


    @Test
    @Transactional // Janela com o banco para realizar operações enquanto o metodo esta em execucao. Usado no modo Lazy
    public void bucarLivroTeste() {
        Livro livro = livroRepository.findById(UUID.fromString("b565ecbc-d70e-4b6f-b5c5-0ab9f46c16e3")).orElse(null);
        System.out.println(livro);

    }


    @Test
    public  void pesquisaDeLivroPorTitulo(){
        List<Livro> livro = livroRepository.findByTituloContainingIgnoreCase("UF");

        livro.forEach(System.out::println);


    }


    @Test
    public void pesquidaDeLivroPorTituloEPreco(){
        var preco = BigDecimal.valueOf(204.00);

        List<Livro> livros = livroRepository.findByTituloAndPreco("UFO",preco);

    }

    @Test
    public void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarTodosOrdenadosPorTitulo();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    public void listarTituloDiferenteLivros(){
        var resultado = livroRepository.listarTitulosDiferentesLivros();
        resultado.forEach(System.out::println);

    }

    @Test
    public void listarGeneroPorNacionalidadeBrasileira(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);

    }

    @Test
    public void listarPorGeneroEOrdernarPorParam(){
        var resultado = livroRepository.findByGenero(GeneroLivro.MISTERIO,"dataPublicacao");
        resultado.forEach(System.out::println);

    }


    @Test
    public void deletePorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }


    @Test
    public void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(1979,01,02),"UFO");

    }
}