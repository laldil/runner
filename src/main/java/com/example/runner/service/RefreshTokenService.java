package com.example.runner.service;

import com.example.runner.entities.RefreshTokenEntity;
import com.example.runner.entities.UserEntity;
import com.example.runner.repositories.RefreshTokenRepository;
import com.example.runner.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @Value("${refresh.token.lifetime}")
    private Duration tokenLifetime;

    public String createRefreshToken(String username) {
        UserEntity userEntity = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with specified name not found"));

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .token(jwtTokenUtils.generateToken(userEntity, tokenLifetime))
                .expirationDate(new Date(System.currentTimeMillis() + tokenLifetime.toMillis()))
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshTokenEntity findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Token not found"));
    }

    public void deleteById(Long id){
        refreshTokenRepository.deleteById(id);
    }
}
