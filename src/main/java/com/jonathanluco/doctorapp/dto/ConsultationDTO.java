package com.jonathanluco.doctorapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO pour la création/mise à jour d'une Consultation.
 */
@Schema(description = "DTO pour la création/mise à jour d'une consultation")
public class ConsultationDTO {

    @NotBlank(message = "Le numéro de consultation ne peut pas être vide")
    @Schema(description = "Numéro de consultation unique", example = "CONS001")
    private String numeroConsultation;

    @NotNull(message = "La date ne peut pas être nulle")
    @Schema(description = "Date et heure de la consultation", example = "2026-07-06T14:30:00")
    private LocalDateTime date;

    @Schema(description = "Numéro SS du patient", example = "123456789012")
    private String patientNumeroSS;

    @Schema(description = "Matricule du médecin", example = "DOC001")
    private String medecinMatricule;

    @Schema(description = "Liste des prescriptions")
    private List<PrescriptionDTO> prescriptions = new ArrayList<>();

    public ConsultationDTO() {
    }

    public ConsultationDTO(String numeroConsultation, LocalDateTime date) {
        this.numeroConsultation = numeroConsultation;
        this.date = date;
    }

    public String getNumeroConsultation() {
        return numeroConsultation;
    }

    public void setNumeroConsultation(String numeroConsultation) {
        this.numeroConsultation = numeroConsultation;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPatientNumeroSS() {
        return patientNumeroSS;
    }

    public void setPatientNumeroSS(String patientNumeroSS) {
        this.patientNumeroSS = patientNumeroSS;
    }

    public String getMedecinMatricule() {
        return medecinMatricule;
    }

    public void setMedecinMatricule(String medecinMatricule) {
        this.medecinMatricule = medecinMatricule;
    }

    public List<PrescriptionDTO> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionDTO> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
