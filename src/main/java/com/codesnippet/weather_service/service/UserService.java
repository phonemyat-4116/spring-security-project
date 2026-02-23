package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.RegisterUserRequest;
import com.codesnippet.weather_service.entity.UserResponse;
import com.codesnippet.weather_service.entity.Users;
import com.codesnippet.weather_service.repository.UserDetailsRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        // TODO check if the user is already present
        if (userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        // TODO encode password in request
        Users users = new Users();
        users.setUsername(registerUserRequest.getUsername());
        users.setRole(registerUserRequest.getRole());
        users.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));

        // TODO to save user
        Users savedUser = userDetailsRepository.save(users);

        // Return as UserResponse
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole().name());
    }

}
