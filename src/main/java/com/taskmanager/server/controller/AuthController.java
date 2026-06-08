package com.taskmanager.server.controller;

import com.taskmanager.server.dto.LoginRequest;
import com.taskmanager.server.dto.LoginResponse;
import com.taskmanager.server.entity.User;
import com.taskmanager.server.service.AuthService;
import com.taskmanager.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // Register new user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.createUser(user);
    }

    // Login user
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request){

        return authService.login(request);
    }
}