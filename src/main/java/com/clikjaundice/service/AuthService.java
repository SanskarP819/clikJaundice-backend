package com.clikjaundice.service;

import com.clikjaundice.dto.AuthDtos.AuthResponse;
import com.clikjaundice.dto.AuthDtos.LoginRequest;
import com.clikjaundice.dto.AuthDtos.SignupRequest;
import com.clikjaundice.model.Doctor;
import com.clikjaundice.repository.DoctorRepository;
import com.clikjaundice.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse signup(SignupRequest request) {
        if (doctorRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken. Please choose another.");
        }

        Doctor doctor = new Doctor();
        doctor.setUsername(request.getUsername().trim().toLowerCase());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setFullName(request.getFullName().trim());
        doctorRepository.save(doctor);

        String token = jwtUtils.generateToken(doctor.getUsername());
        return new AuthResponse(token, doctor.getUsername(), doctor.getFullName(), "Signup successful");
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim().toLowerCase(),
                        request.getPassword()
                )
        );

        Doctor doctor = doctorRepository.findByUsername(request.getUsername().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        String token = jwtUtils.generateToken(doctor.getUsername());
        return new AuthResponse(token, doctor.getUsername(), doctor.getFullName(), "Login successful");
    }
}