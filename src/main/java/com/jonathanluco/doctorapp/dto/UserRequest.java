package com.jonathanluco.doctorapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String username,
        @NotBlank
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[^\\p{Alnum}]).{8,}$",
                message = "Le mot de passe doit contenir au moins un chiffre et un caractere special"
        )
        String password
) {
}
