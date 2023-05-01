package com.diel.dev.identityservice.services;

import com.diel.dev.identityservice.dtos.CredentialsRequestDTO;
import com.diel.dev.identityservice.dtos.JWTResponseDTO;
import com.diel.dev.identityservice.dtos.UserRequestDTO;
import com.diel.dev.identityservice.dtos.UserResponseDTO;
import com.diel.dev.identityservice.entities.Authority;
import com.diel.dev.identityservice.entities.UserEntity;
import com.diel.dev.identityservice.exceptions.BusinessException;
import com.diel.dev.identityservice.mappers.EntityMapper;
import com.diel.dev.identityservice.repositories.UserRepository;
import com.diel.dev.identityservice.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil tokenUtil;

    public UserResponseDTO save(UserRequestDTO requestDTO) {
        UserEntity user = mapper.toEntity(requestDTO);
        if (isUsernameInUse(user.getUsername())) {
            throw new BusinessException("Username already in use!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return mapper.toDTO(user);
    }

    public JWTResponseDTO attemptAuthenticate(CredentialsRequestDTO requestDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            Authority authority = getUserAuthority(requestDTO.getUsername());
            return new JWTResponseDTO(tokenUtil.generateJWT(requestDTO.getUsername(), authority));
        }
        throw new BusinessException("Authentication error");
    }

    private Authority getUserAuthority(String username) {
        Optional<UserEntity> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new BusinessException("User not found");
        }
        return user.get().getAuthority();
    }

    private boolean isUsernameInUse(String username) {
        return repository.findByUsername(username).isPresent();
    }
}
