package com.viakids.controller;

import com.viakids.model.dto.LoginRequest;
import com.viakids.model.dto.LoginResponse;
import com.viakids.model.User;
import com.viakids.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User created = authService.register(user);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = authService.getProfile(email);
        return ResponseEntity.ok(user);
    }
}
