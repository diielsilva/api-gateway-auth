package com.diel.dev.identityservice.mappers;

import com.diel.dev.identityservice.dtos.UserRequestDTO;
import com.diel.dev.identityservice.dtos.UserResponseDTO;
import com.diel.dev.identityservice.entities.Authority;
import com.diel.dev.identityservice.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public UserEntity toEntity(UserRequestDTO requestDTO) {
        return new UserEntity(null, requestDTO.getUsername(), requestDTO.getPassword(),
                Authority.toEnum(requestDTO.getAuthority()));
    }

    public UserResponseDTO toDTO(UserEntity user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getAuthority());
    }
}
