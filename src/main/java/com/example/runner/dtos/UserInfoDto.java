package com.example.runner.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoDto {
    private String username;
    private String email;
    private Boolean isMale;
    private String dateOfBirth;
    private Integer weight;
    private Integer height;
    private Double fitPercentage;
    private Integer coins;
    private String location;
    private Double totalKm;
    private String groupName;
}
