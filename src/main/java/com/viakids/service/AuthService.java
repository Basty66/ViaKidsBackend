package com.viakids.service;

import com.viakids.model.User;
import com.viakids.model.dto.LoginRequest;
import com.viakids.model.dto.LoginResponse;
import com.viakids.repository.UserRepository;
import com.viakids.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    
    public LoginResponse login(LoginRequest request) {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + request.getEmail());
        
        // TEMPORARY: Hardcoded login for testing
        if ("admin@viakids.cl".equals(request.getEmail()) && "admin123".equals(request.getPassword())) {
            String token = jwtUtil.generateToken("admin@viakids.cl", "ADMIN");
            return new LoginResponse(token, "admin", "Admin ViaKids");
        }
        
        // Normal authentication
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRol().name());
            
            return new LoginResponse(token, user.getRol().name().toLowerCase(), user.getNombre());
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            throw new RuntimeException("Credenciales inválidas");
        }
    }
    
    public User register(User user) {
        System.out.println("=== REGISTER ===");
        System.out.println("Raw password: " + user.getPassword());
        String encoded = passwordEncoder.encode(user.getPassword());
        System.out.println("Encoded password: " + encoded);
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        user.setPassword(encoded);
        return userRepository.save(user);
    }
    
    public User getProfile(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
