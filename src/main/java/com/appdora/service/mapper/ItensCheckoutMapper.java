package com.appdora.service.mapper;

import com.appdora.domain.*;
import com.appdora.service.dto.ItensCheckoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItensCheckout and its DTO ItensCheckoutDTO.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItensCheckoutMapper extends EntityMapper<ItensCheckoutDTO, ItensCheckout> {

    @Mapping(source = "produto.id", target = "produtoId")
    ItensCheckoutDTO toDto(ItensCheckout itensCheckout);

    @Mapping(source = "produtoId", target = "produto")
    @Mapping(target = "checkouts", ignore = true)
    ItensCheckout toEntity(ItensCheckoutDTO itensCheckoutDTO);

    default ItensCheckout fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItensCheckout itensCheckout = new ItensCheckout();
        itensCheckout.setId(id);
        return itensCheckout;
    }
}
