package com.taskmanager.server.controller;

import com.taskmanager.server.dto.LoginRequest;
import com.taskmanager.server.dto.LoginResponse;
import com.taskmanager.server.entity.User;
import com.taskmanager.server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        if(request.getEmail().equals("admin@gmail.com") && request.getPassword().equals("123450")){
            String token =  jwtUtil.generateToken(request.getEmail());

            return new LoginResponse(token,1L);

        }

        return null;
    }

}
