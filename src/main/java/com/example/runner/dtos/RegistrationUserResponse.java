package com.example.runner.dtos;

import com.example.runner.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationUserResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private Boolean isMale;
    private String dateOfBirth;
    private Integer weight;
    private Integer height;
    private Double fitPercentage;
    private String location;
    private List<Integer> selectedGoalsIndexes;
    private List<Integer> selectedSplitIndexes;
    private Collection<RoleEntity> roles;
}
