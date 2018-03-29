package com.appdora.service.mapper;

import com.appdora.domain.*;
import com.appdora.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produto and its DTO ProdutoDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

    @Mapping(source = "categoria", target = "categoriaDTO")
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProdutoDTO toDto(Produto produto);

    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(target = "checkouts", ignore = true)
    Produto toEntity(ProdutoDTO produtoDTO);

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
