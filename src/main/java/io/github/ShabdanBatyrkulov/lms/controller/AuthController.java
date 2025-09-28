package io.github.ShabdanBatyrkulov.lms.controller;

import io.github.ShabdanBatyrkulov.lms.dto.UserRegistrationDto;
import io.github.ShabdanBatyrkulov.lms.dto.UserLoginDto;
import io.github.ShabdanBatyrkulov.lms.model.User;
import io.github.ShabdanBatyrkulov.lms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // -------------------------
    // Registration endpoint
    // -------------------------
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto registrationDto) {
        try {
            userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // -------------------------
    // Login endpoint
    // -------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        boolean passwordMatches = passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash());
        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
