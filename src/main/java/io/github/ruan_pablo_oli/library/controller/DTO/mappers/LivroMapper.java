package io.github.ruan_pablo_oli.library.controller.DTO.mappers;

import io.github.ruan_pablo_oli.library.controller.DTO.CadastroLivroDTO;
import io.github.ruan_pablo_oli.library.model.Livro;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);
}
