package com.jonathanluco.doctorapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "consultations")
public class Consultation {

    @Id
    private String numeroConsultation;

    @NotBlank
    private LocalDateTime date;

    @DBRef
    private Patient patientAssiste;

    @DBRef
    private Medecin medecinDonne;

    private List<Prescription> prescriptions = new ArrayList<>();

    public Consultation() {
    }

    public Consultation(String numeroConsultation, LocalDateTime date) {
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

    public Patient getPatientAssiste() {
        return patientAssiste;
    }

    public void setPatientAssiste(Patient patientAssiste) {
        this.patientAssiste = patientAssiste;
    }

    public Medecin getMedecinDonne() {
        return medecinDonne;
    }

    public void setMedecinDonne(Medecin medecinDonne) {
        this.medecinDonne = medecinDonne;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }
}
