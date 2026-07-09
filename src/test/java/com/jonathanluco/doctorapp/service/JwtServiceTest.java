package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtService Tests")
class JwtServiceTest {

    private final JwtServiceImpl jwtService = new JwtServiceImpl(
            "12345678901234567890123456789012",
            60000
    );

    @Test
    void shouldGenerateAndValidateToken() {
        UserDetails userDetails = User.withUsername("user@example.com").password("pwd").authorities("ROLE_USER").build();

        String token = jwtService.generateToken(userDetails);

        assertEquals("user@example.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }
}
