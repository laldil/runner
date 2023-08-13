package com.example.runner.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class RegistrationUserRequest {
    @NotNull(message = "Введите имя пользователя")
    private String username;

    @NotNull(message = "Введите пароль")
    @Pattern(regexp = "^.{8,}$", message = "Пароль должен состоять как минимум из 8 символов")
    private String password;

    @NotNull(message = "Введите почту")
    @Email(message = "Введите корректную почту")
    private String email;

    @NotNull(message = "Введите пол")
    private Boolean isMale;

    @NotNull(message = "Введите дату рождения")
    private String dateOfBirth;

    @NotNull(message = "Введите вес")
    private Integer weight;

    @NotNull(message = "Введите рост")
    private Integer height;

    @NotNull(message = "fitPercentage не может быть пустым")
    private Double fitPercentage;

    @NotNull(message = "Введите локацию")
    private String location;

    @NotNull(message = "selectedGoalsIndexes не может быть пустым")
    private List<Integer> selectedGoalsIndexes;

    @NotNull(message = "selectedSplitIndexes не может быть пустым")
    private List<Integer> selectedSplitIndexes;
}
