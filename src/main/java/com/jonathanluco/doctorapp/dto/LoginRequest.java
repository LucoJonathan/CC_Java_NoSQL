package com.jonathanluco.doctorapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Requete de connexion.
 */
public record LoginRequest(
        @NotBlank
        @Email(message = "Le format de l'email est invalide")
        String email,
        @NotBlank String password
) {
}
