package com.jonathanluco.doctorapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caracteres") String password
) {
}
