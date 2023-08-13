package com.example.runner.service;

import com.example.runner.dtos.LoginRequest;
import com.example.runner.dtos.LoginResponse;
import com.example.runner.dtos.RegistrationUserRequest;
import com.example.runner.dtos.RegistrationUserResponse;
import com.example.runner.entities.UserEntity;
import com.example.runner.exceptions.UserAlreadyExistsException;
import com.example.runner.mappers.UserMapper;
import com.example.runner.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        String token = authenticateAndGetJwtToken(loginRequest.getUsername(), loginRequest.getUsername());

        UserEntity userEntity = userService.findByUsername(loginRequest.getUsername()).get();

        LoginResponse loginResponse = UserMapper.INSTANCE.userEntityToLoginResponse(userEntity);
        loginResponse.setToken(token);

        return loginResponse;
    }

    public RegistrationUserResponse registration(RegistrationUserRequest request) throws UserAlreadyExistsException {
        if (userService.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("The user with the specified name already exists");
        }

        UserEntity user = userService.createNewUser(request);

        String token = authenticateAndGetJwtToken(user.getUsername(), user.getPassword());
        RegistrationUserResponse response = UserMapper.INSTANCE.userEntityToRegistrationResponse(user);
        response.setToken(token);
        return response;
    }

    private String authenticateAndGetJwtToken(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login or password is not correct");
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails);
    }
}
