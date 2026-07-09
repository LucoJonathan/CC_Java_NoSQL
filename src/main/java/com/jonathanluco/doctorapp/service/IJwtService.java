package com.jonathanluco.doctorapp.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contrat JWT.
 */
public interface IJwtService {
    /**
     * Genere token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Extrait username.
     */
    String extractUsername(String token);

    /**
     * Valide token.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
