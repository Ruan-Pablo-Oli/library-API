package io.github.ruan_pablo_oli.library.controller.DTO.mappers;


import io.github.ruan_pablo_oli.library.controller.DTO.AutorDTO;
import io.github.ruan_pablo_oli.library.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome",target = "nome")
    @Mapping(source = "dataNascimento",target = "dataNascimento")
    @Mapping(source = "nacionalidade",target = "nacionalidade" // É possível mapear propriedades com nomes diferentes

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
