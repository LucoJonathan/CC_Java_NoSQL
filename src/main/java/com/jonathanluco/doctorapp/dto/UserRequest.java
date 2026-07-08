package com.jonathanluco.doctorapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Requete de creation/mise a jour d'utilisateur.
 */
public record UserRequest(
        @NotBlank
        @Email(message = "Le format de l'email est invalide")
        String email,
        @NotBlank
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[^\\p{Alnum}]).{8,}$",
                message = "Le mot de passe doit contenir au moins un chiffre et un caractere special"
        )
        String password
) {
}
