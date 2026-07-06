package com.jonathanluco.doctorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour la création/mise à jour d'un Médecin.
 */
@Schema(description = "DTO pour la création/mise à jour d'un médecin")
public class MedecinDTO {

    @NotBlank(message = "Le matricule ne peut pas être vide")
    @Schema(description = "Matricule du médecin", example = "DOC001")
    private String matricule;

    @NotBlank(message = "Le nom du médecin ne peut pas être vide")
    @Schema(description = "Nom complet du médecin", example = "Dr. Martin Lefebvre")
    private String nomMedecin;

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Schema(description = "Nom d'utilisateur", example = "dr_martin")
    private String username;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Schema(description = "Mot de passe", example = "password123")
    private String password;

    public MedecinDTO() {
    }

    public MedecinDTO(String matricule, String nomMedecin, String username, String password) {
        this.matricule = matricule;
        this.nomMedecin = nomMedecin;
        this.username = username;
        this.password = password;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomMedecin() {
        return nomMedecin;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
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
