package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.LoginRequest;
import com.jonathanluco.doctorapp.dto.LoginResponse;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.service.IJwtService;
import com.jonathanluco.doctorapp.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controleur d'authentification (login JWT).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final IJwtService jwtService;

    public AuthController(IUserService userService, IJwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userService.authenticate(request.email(), request.password());

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails, java.util.Map.of("role", user.getRole().name()));
        return new LoginResponse(token, "Bearer", user.getRole().name());
    }
}
