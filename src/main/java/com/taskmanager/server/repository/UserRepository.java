package com.taskmanager.server.repository;

import com.taskmanager.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user using email
    Optional<User> findByEmail(String email);
}