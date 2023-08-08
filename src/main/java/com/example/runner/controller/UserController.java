package com.example.runner.controller;

import com.example.runner.dtos.UpdateUserDto;
import com.example.runner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController(value = "/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> userInfoDto(@RequestBody UpdateUserDto dto, Principal principal){
        try {
            return ResponseEntity.ok().body(userService.updateUser(principal.getName(), dto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
