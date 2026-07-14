package com.jonathanluco.doctorapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Contrat JWT.
 */
public interface IJwtService {
    /**
     * Genere token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Genere token en incluant des claims additionnels dans le payload (ex: role).
     */
    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

    /**
     * Extrait username.
     */
    String extractUsername(String token);

    /**
     * Extrait le role stocke dans le payload du token.
     */
    String extractRole(String token);

    /**
     * Valide token.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
