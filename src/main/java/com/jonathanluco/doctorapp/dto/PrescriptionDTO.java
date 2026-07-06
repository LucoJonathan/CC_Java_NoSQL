package com.jonathanluco.doctorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

/**
 * DTO pour la création/mise à jour d'une Prescription.
 */
@Schema(description = "DTO pour la création/mise à jour d'une prescription")
public class PrescriptionDTO {

    @NotBlank(message = "Le code du médicament ne peut pas être vide")
    @Schema(description = "Code du médicament prescrit", example = "MED001")
    private String codeMedicament;

    @Min(value = 1, message = "Le nombre de prises doit être au minimum 1")
    @Schema(description = "Nombre de prises prescrites", example = "3")
    private int nbPrise;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(String codeMedicament, int nbPrise) {
        this.codeMedicament = codeMedicament;
        this.nbPrise = nbPrise;
    }

    public String getCodeMedicament() {
        return codeMedicament;
    }

    public void setCodeMedicament(String codeMedicament) {
        this.codeMedicament = codeMedicament;
    }

    public int getNbPrise() {
        return nbPrise;
    }

    public void setNbPrise(int nbPrise) {
        this.nbPrise = nbPrise;
    }
}
