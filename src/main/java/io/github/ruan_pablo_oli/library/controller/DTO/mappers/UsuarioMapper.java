package io.github.ruan_pablo_oli.library.controller.DTO.mappers;


import io.github.ruan_pablo_oli.library.controller.DTO.UsuarioDTO;
import io.github.ruan_pablo_oli.library.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);


}
