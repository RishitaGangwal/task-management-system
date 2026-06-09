package com.taskmanager.server.controller;

import com.taskmanager.server.entity.User;
import com.taskmanager.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")                   //base URL for all APIs in this controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


}


