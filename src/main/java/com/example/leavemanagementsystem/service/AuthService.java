package com.example.leavemanagementsystem.service;

import com.example.leavemanagementsystem.dto.JwtAuthResponse;
import com.example.leavemanagementsystem.dto.LoginRequest;
import com.example.leavemanagementsystem.dto.SignUpRequest;
import com.example.leavemanagementsystem.model.Role;
import com.example.leavemanagementsystem.model.User;
import com.example.leavemanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse signup(SignUpRequest request) {
        User manager = null;
        if (request.getRole() == Role.EMPLOYEE && request.getManagerEmail() != null) {
            manager = userRepository.findByEmail(request.getManagerEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            if (manager.getRole() != Role.MANAGER) {
                throw new IllegalArgumentException("Assigned manager is not a manager");
            }
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .manager(manager)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwt);
        return response;
    }

    public JwtAuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwt);
        return response;
    }
}