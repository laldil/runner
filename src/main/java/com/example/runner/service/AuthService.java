package com.example.runner.service;

import com.example.runner.dtos.LoginRequest;
import com.example.runner.dtos.LoginResponse;
import com.example.runner.dtos.RefreshTokenDto;
import com.example.runner.dtos.RefreshTokenResponse;
import com.example.runner.dtos.RegistrationUserRequest;
import com.example.runner.dtos.RegistrationUserResponse;
import com.example.runner.entities.RefreshTokenEntity;
import com.example.runner.entities.UserEntity;
import com.example.runner.exceptions.UserAlreadyExistsException;
import com.example.runner.mappers.UserMapper;
import com.example.runner.utils.JwtTokenUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest loginRequest) {
        String token = authenticateAndGetJwtToken(loginRequest.getUsername(), loginRequest.getPassword());
        String refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

        UserEntity userEntity = userService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", loginRequest.getUsername())));

        LoginResponse loginResponse = UserMapper.INSTANCE.userEntityToLoginResponse(userEntity);
        loginResponse.setAccessToken(token);
        loginResponse.setRefreshToken(refreshToken);

        return loginResponse;
    }

    public RegistrationUserResponse registration(RegistrationUserRequest request) throws UserAlreadyExistsException {
        UserEntity user = userService.createNewUser(request);
        String token = authenticateAndGetJwtToken(request.getUsername(), request.getPassword());
        String refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        RegistrationUserResponse response = UserMapper.INSTANCE.userEntityToRegistrationResponse(user);
        response.setAccessToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }

    public RefreshTokenResponse refreshAccessToken(RefreshTokenDto dto) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(dto.getToken());
        if (!jwtTokenUtils.validateJwtToken(refreshToken.getToken())) {
            refreshTokenService.deleteById(refreshToken.getId());
            throw new JwtException("Token is not valid");
        }
        String accessToken = jwtTokenUtils.generateToken(refreshToken.getUser());
        return new RefreshTokenResponse(accessToken, refreshToken.getToken());
    }

    private String authenticateAndGetJwtToken(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login or password is not correct");
        }
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with specified name not found"));
        return jwtTokenUtils.generateToken(user);
    }
}
