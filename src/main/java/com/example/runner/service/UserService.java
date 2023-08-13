package com.example.runner.service;

import com.example.runner.dtos.RegistrationUserRequest;
import com.example.runner.dtos.UpdateUserDto;
import com.example.runner.dtos.UserInfoDto;
import com.example.runner.entities.UserEntity;
import com.example.runner.mappers.UserMapper;
import com.example.runner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, RoleService roleService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found  ", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public UserEntity createNewUser(RegistrationUserRequest request) {
        UserEntity user = UserMapper.INSTANCE.registrationUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public UserInfoDto getUserInfo(String username) {
        UserEntity user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found  ", username)
        ));

        return UserMapper.INSTANCE.userToUserInfoDto(user);
    }

    public UserInfoDto updateUser(String username, UpdateUserDto updateUserDto){
        if (userRepository.findByUsername(username).isEmpty()){
            throw new UsernameNotFoundException("User %s not found");
        }
        UserEntity user = userRepository.findByUsername(username).get();
        user = UserMapper.INSTANCE.updateUserDtoToUser(updateUserDto);
        userRepository.save(user);
        return UserMapper.INSTANCE.userToUserInfoDto(user);
    }
}
