package com.appdora.service.mapper;

import com.appdora.domain.*;
import com.appdora.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TagMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "tag", target = "tagDTO")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(target = "checkout", ignore = true)
    @Mapping(source = "tagDTO", target = "tag")
    Cliente toEntity(ClienteDTO clienteDTO);

    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
