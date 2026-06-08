package com.taskmanager.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    String email;
    String password;
}
