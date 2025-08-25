package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.controller.DTO.CadastroLivroDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.ErroResposta;
import io.github.ruan_pablo_oli.library.controller.DTO.ResultadoPesquisaLivroDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.mappers.LivroMapper;
import io.github.ruan_pablo_oli.library.exceptions.registroDuplicadoException;
import io.github.ruan_pablo_oli.library.model.GeneroLivro;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController  implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;



    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO){
        Livro livro = livroMapper.toEntity(cadastroLivroDTO);
        livroService.salvar(livro);
        var url = this.gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id){
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            var dto = livroMapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet( () -> ResponseEntity.notFound().build());

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable String id){
        return livroService.obterPorId(UUID.fromString(id)).map(
                livro -> {
                    livroService.deleteLivro(livro);
                    return ResponseEntity.noContent().build();
                }
        ).orElseGet( () -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(@RequestParam(value="isbn",required = false) String isbn, @RequestParam(value="titulo",required = false) String titulo,
                                                                    @RequestParam(value="nome-autor",required = false) String nomeAutor,
                                                                    @RequestParam(value="genero",required = false) GeneroLivro genero,
                                                                    @RequestParam(value="ano-publicacao",required = false) Integer anoPublicacao
                                                                    ){
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado.stream().map(livroMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = livroMapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
