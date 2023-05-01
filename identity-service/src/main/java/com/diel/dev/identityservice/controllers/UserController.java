package com.diel.dev.identityservice.controllers;

import com.diel.dev.identityservice.dtos.CredentialsRequestDTO;
import com.diel.dev.identityservice.dtos.JWTResponseDTO;
import com.diel.dev.identityservice.dtos.UserRequestDTO;
import com.diel.dev.identityservice.dtos.UserResponseDTO;
import com.diel.dev.identityservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("signup")
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO responseDTO = service.save(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("auth")
    public ResponseEntity<JWTResponseDTO> attemptAuthenticate(@RequestBody CredentialsRequestDTO requestDTO) {
        JWTResponseDTO responseDTO = service.attemptAuthenticate(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
