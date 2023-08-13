package com.example.runner.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequest {
    @NotNull(message = "Введите имя пользователя")
    private String username;

    @NotNull(message = "Введите пароль")
    @Pattern(regexp = "^.{8,}$", message = "Пароль должен состоять как минимум из 8 символов")
    private String password;
}