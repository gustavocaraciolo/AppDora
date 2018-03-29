package com.appdora.service.mapper;

import com.appdora.domain.*;
import com.appdora.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TagMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "name")
    @Mapping(source = "tag.id", target = "tagId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "tag", target = "tagDTO")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "checkouts", ignore = true)
    @Mapping(source = "tagId", target = "tag")
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
