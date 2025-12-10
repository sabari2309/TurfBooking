package com.example.spring.data.rest.Controller;

import com.example.spring.data.rest.Service.OtpService;
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
    private final OtpService otpService;

    public AuthController(OtpService otpService) {
        this.otpService = otpService;
    }
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

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        Optional<User> existingUser = customerRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return ResponseEntity.status(400).body("User already exists");
        }

        return ResponseEntity.ok("Email available");
    }


    @GetMapping("/send-email-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String msg = otpService.sendOtp(email);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/verify-email-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {
        boolean valid = otpService.verifyOtp(email, otp);
        if (valid) {
            return ResponseEntity.ok("Email Verified Successfully!");
        }
        return ResponseEntity.status(400).body("Invalid OTP");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("password");

        Optional<User> user = customerRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User u = user.get();
        u.setPassword(newPassword);
        customerRepository.save(u);

        return ResponseEntity.ok("Password updated successfully!");
    }

}
