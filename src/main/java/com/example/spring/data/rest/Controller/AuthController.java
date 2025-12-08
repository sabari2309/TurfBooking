package com.example.spring.data.rest.Controller;

import com.example.spring.data.rest.model.User;
import com.example.spring.data.rest.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="*")
public class AuthController {
    @Autowired
    private CustomerRepo customerRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {

        Optional<User> existingUser = customerRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(400).body("User already exists!");
        }

        customerRepository.save(user);
        return ResponseEntity.ok("Signup successful!");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Optional<User> existingUser = customerRepository.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User foundUser = existingUser.get();

        if (!foundUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid Password");
        }

        if (foundUser.getEmail().equals("admin@gmail.com")) {
            return ResponseEntity.ok(Map.of("role", "ADMIN", "name", "Admin"));
        }

        return ResponseEntity.ok(foundUser);
    }

}
