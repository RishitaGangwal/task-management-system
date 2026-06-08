package com.taskmanager.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    // Primary key for user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User full name
    private String name;

    // Email must be unique
    @Column(unique = true, nullable = false)
    private String email;

    // BCrypt encrypted password
    private String password;

    // USER or ADMIN
    private String role;
}