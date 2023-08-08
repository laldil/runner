package com.example.runner.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
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
