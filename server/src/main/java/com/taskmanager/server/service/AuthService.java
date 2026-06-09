package com.taskmanager.server.service;

import com.taskmanager.server.dto.LoginRequest;
import com.taskmanager.server.dto.LoginResponse;
import com.taskmanager.server.entity.User;
import com.taskmanager.server.repository.UserRepository;
import com.taskmanager.server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Authenticate user and generate JWT token
    public LoginResponse login(LoginRequest request){

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Verify encrypted password
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if(!passwordMatches){
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token =
                jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token, user.getId());
    }
}