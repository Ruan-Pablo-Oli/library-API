package io.github.ruan_pablo_oli.library.controller;

import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController {


    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autorDTO){
        var autor = autorDTO.mapearAutor();
        autorService.salvar(autor);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);
        if(autor.isPresent()){
            Autor entidade = autor.get();
            AutorDTO dto = new AutorDTO(entidade.getId(),entidade.getNome(),entidade.getDataNascimento(),entidade.getNacionalidade());
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);
        if(autor.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.deletarPorId(autor.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome",required = false) String nome, @RequestParam(value = "nacionalidade",required = false) String nacionalidade){
        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento(),autor.getNacionalidade())).collect(Collectors.toList());
        return ResponseEntity.ok().body(lista);
    }

}
