package com.taskmanager.server.service;

import com.taskmanager.server.entity.User;
import com.taskmanager.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Used for password encryption
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create new user
    public User createUser(User user){

        // Encrypt password before storing in DB
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Default role
        if(user.getRole() == null){
            user.setRole("USER");
        }

        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}