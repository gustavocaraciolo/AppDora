package com.appdora.service.mapper;

import com.appdora.domain.*;
import com.appdora.service.dto.CheckoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Checkout and its DTO CheckoutDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ProdutoMapper.class})
public interface CheckoutMapper extends EntityMapper<CheckoutDTO, Checkout> {

    @Mapping(source = "cliente", target = "clienteDTO")
    @Mapping(source = "cliente.id", target = "clienteId")
    CheckoutDTO toDto(Checkout checkout);

    @Mapping(source = "clienteId", target = "cliente")
    Checkout toEntity(CheckoutDTO checkoutDTO);

    default Checkout fromId(Long id) {
        if (id == null) {
            return null;
        }
        Checkout checkout = new Checkout();
        checkout.setId(id);
        return checkout;
    }
}
