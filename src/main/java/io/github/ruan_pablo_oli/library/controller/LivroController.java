package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.controller.DTO.CadastroLivroDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.ErroResposta;
import io.github.ruan_pablo_oli.library.controller.DTO.mappers.LivroMapper;
import io.github.ruan_pablo_oli.library.exceptions.registroDuplicadoException;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;



    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO cadastroLivroDTO){
        try{
            Livro livro = livroMapper.toEntity(cadastroLivroDTO);
            livroService.salvar(livro);
            return ResponseEntity.ok(livro);
        }catch (registroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
