package io.github.ruan_pablo_oli.library.controller;

import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.ErroResposta;
import io.github.ruan_pablo_oli.library.controller.DTO.mappers.AutorMapper;
import io.github.ruan_pablo_oli.library.exceptions.OperacaoNaoPermitidaException;
import io.github.ruan_pablo_oli.library.exceptions.registroDuplicadoException;
import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.model.Usuario;
import io.github.ruan_pablo_oli.library.security.SecurityService;
import io.github.ruan_pablo_oli.library.service.AutorService;
import io.github.ruan_pablo_oli.library.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
@Tag(name = "Autores")
public class AutorController implements GenericController {


    private final AutorService autorService;
    private final AutorMapper autorMapper;


    //@Valid já valida usando o validation
    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novos autores")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201",description = "Cadastrado com sucesso."),
                    @ApiResponse(responseCode = "422",description = "Erro de validação."),
                    @ApiResponse(responseCode = "409",description = "Autor já cadastrado.")
            }
    )
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {

        Autor autor = autorMapper.toEntity(autorDTO);
        autorService.salvar(autor);
        URI location = this.gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Obter Detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200",description = "Autor encontrado."),
                    @ApiResponse(responseCode = "404",description = "Autor não encontrado.")
            }
    )
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor).map(autor -> {
            AutorDTO dto = autorMapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "204",description = "Deletado com sucesso."),
                    @ApiResponse(responseCode = "404",description = "Autor não encontrado."),
                    @ApiResponse(responseCode = "404",description = "Autor possui livro cadastrado."),

            }
    )
    public ResponseEntity<Void> deletarPorId(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);
        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletarPorId(autor.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR','GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza a pesquisa de  autores por parâmetros ")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200",description = "Sucesso.")

            }
    )
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um autor existente")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "204",description = "Atualizado com sucesso."),
                    @ApiResponse(responseCode = "404",description = "Autor não encontrado."),
                    @ApiResponse(responseCode = "409",description = "Autor já cadastrado."),

            }
    )
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        return ResponseEntity.ok().body(autorService.atualizar(UUID.fromString(id), dto));
    }
}
