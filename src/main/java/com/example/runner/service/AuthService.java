package com.example.runner.service;

import com.example.runner.dtos.JwtRequest;
import com.example.runner.dtos.JwtResponse;
import com.example.runner.dtos.RegistrationUserDto;
import com.example.runner.dtos.UserDto;
import com.example.runner.entities.User;
import com.example.runner.exceptions.UserAlreadyExistsException;
import com.example.runner.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login or password is not correct");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    public UserDto createNewUser(@RequestBody RegistrationUserDto registrationUserDto) throws UserAlreadyExistsException {
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("The user with the specified name already exists");
        }
        User user = userService.createNewUser(registrationUserDto);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
