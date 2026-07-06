package com.jonathanluco.doctorapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une Consultation médicale.
 * MongoDB collection: "consultations"
 *
 * Une consultation est un enregistrement d'une visite médicale,
 * engageant un Patient, un Médecin, et éventuellement plusieurs Médicaments (via Prescriptions).
 *
 * Relation Patient → Consultation : 1,N (Assiste)
 * Relation Medecin → Consultation : 0,N (Donne)
 * Relation Medicament ↔ Consultation : 0,N (Prescrit)
 *
 * @author Jonathan Luco
 * @version 1.0
 */
@Document(collection = "consultations")
public class Consultation {

    /**
     * Identifiant MongoDB unique - Clé primaire.
     */
    @Id
    private String id;

    /**
     * Numéro de consultation unique pour les références métier.
     */
    @NotBlank
    @Indexed(unique = true)
    private String numeroConsultation;

    /**
     * Date et heure de la consultation.
     */
    @NotBlank
    private LocalDateTime date;

    /**
     * Référence au Patient qui assiste à la consultation.
     * Relation Many-to-One via @DBRef (MongoDB reference).
     */
    @DBRef
    private Patient patientAssiste;

    /**
     * Référence au Médecin qui donne la consultation.
     * Relation Many-to-One via @DBRef (MongoDB reference).
     */
    @DBRef
    private Medecin medecinDonne;

    /**
     * Liste des prescriptions associées à cette consultation.
     */
    private List<Prescription> prescriptions = new ArrayList<>();

    public Consultation() {
    }

    public Consultation(String numeroConsultation, LocalDateTime date) {
        this.numeroConsultation = numeroConsultation;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
