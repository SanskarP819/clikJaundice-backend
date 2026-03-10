package com.clikjaundice.controller;

import com.clikjaundice.dto.AuthDtos.AuthResponse;
import com.clikjaundice.dto.AuthDtos.LoginRequest;
import com.clikjaundice.dto.AuthDtos.SignupRequest;
import com.clikjaundice.dto.TestDtos.ApiResponse;
import com.clikjaundice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(@RequestBody SignupRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isBlank())
                return ResponseEntity.badRequest().body(ApiResponse.error("Username is required"));
            if (request.getPassword() == null || request.getPassword().length() < 6)
                return ResponseEntity.badRequest().body(ApiResponse.error("Password must be at least 6 characters"));
            if (request.getFullName() == null || request.getFullName().isBlank())
                return ResponseEntity.badRequest().body(ApiResponse.error("Full name is required"));

            AuthResponse response = authService.signup(request);
            return ResponseEntity.ok(ApiResponse.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid username or password"));
        }
    }
}