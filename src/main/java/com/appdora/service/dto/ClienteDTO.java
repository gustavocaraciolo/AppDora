package com.appdora.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * A DTO for the Cliente entity.
 */

public class ClienteDTO implements Serializable {

    private Long id;

    private String telefone;

    private Long userId;

    private Long tagId;

    private UserDTO userDTO;

    private TagDTO tagDTO;

    private String name;

    private String email;

    public ClienteDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getTagId() {
        return this.tagId;
    }

    public UserDTO getUserDTO() {
        return this.userDTO;
    }

    public TagDTO getTagDTO() {
        return this.tagDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setTagDTO(TagDTO tagDTO) {
        this.tagDTO = tagDTO;
    }
}
