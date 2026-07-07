package com.jonathanluco.doctorapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création/mise à jour d'un Patient.
 */
@Schema(description = "DTO pour la création/mise à jour d'un patient")
public class PatientDTO {

    @NotBlank(message = "Le numéro de sécurité sociale ne peut pas être vide")
    @Schema(description = "Numéro de sécurité sociale", example = "123456789012")
    private String numeroSS;

    @NotBlank(message = "Le nom du patient ne peut pas être vide")
    @Schema(description = "Nom complet du patient", example = "Jean Dupont")
    private String nomPatient;

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Schema(description = "Nom d'utilisateur", example = "jean_dupont")
    private String username;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[^\\p{Alnum}]).{8,}$",
            message = "Le mot de passe doit contenir au moins un chiffre et un caractere special"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Mot de passe", example = "MotDePasse1!")
    private String password;

    public PatientDTO() {
    }

    public PatientDTO(String numeroSS, String nomPatient, String username, String password) {
        this.numeroSS = numeroSS;
        this.nomPatient = nomPatient;
        this.username = username;
        this.password = password;
    }

    public String getNumeroSS() {
        return numeroSS;
    }

    public void setNumeroSS(String numeroSS) {
        this.numeroSS = numeroSS;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
