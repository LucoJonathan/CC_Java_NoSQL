package com.jonathanluco.doctorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour la création/mise à jour d'un Médicament.
 */
@Schema(description = "DTO pour la création/mise à jour d'un médicament")
public class MedicamentDTO {

    @NotBlank(message = "Le code du médicament ne peut pas être vide")
    @Schema(description = "Code du médicament", example = "MED001")
    private String code;

    @NotBlank(message = "Le libellé ne peut pas être vide")
    @Schema(description = "Libellé/Nom du médicament", example = "Aspirin 500mg")
    private String libelle;

    public MedicamentDTO() {
    }

    public MedicamentDTO(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
