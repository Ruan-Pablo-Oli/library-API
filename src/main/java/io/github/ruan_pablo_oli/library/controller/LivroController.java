package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.controller.DTO.CadastroLivroDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.ErroResposta;
import io.github.ruan_pablo_oli.library.controller.DTO.ResultadoPesquisaLivroDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.mappers.LivroMapper;
import io.github.ruan_pablo_oli.library.exceptions.registroDuplicadoException;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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
}
