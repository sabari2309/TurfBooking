package com.example.spring.data.rest.repo;

import com.example.spring.data.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
