package com.example.runner.controller;

import com.example.runner.dtos.LoginRequest;
import com.example.runner.dtos.RefreshTokenDto;
import com.example.runner.dtos.RegistrationUserRequest;
import com.example.runner.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody @Valid LoginRequest authRequest) {
        try {
            return ResponseEntity.ok().body(authService.login(authRequest));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationUserRequest request) {
        try {
            return ResponseEntity.ok().body(authService.registration(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenDto dto) {
        try {
            return ResponseEntity.ok().body(authService.refreshAccessToken(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}