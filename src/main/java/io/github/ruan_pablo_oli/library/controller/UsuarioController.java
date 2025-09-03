package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.controller.DTO.UsuarioDTO;
import io.github.ruan_pablo_oli.library.controller.DTO.mappers.UsuarioMapper;
import io.github.ruan_pablo_oli.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }

}
