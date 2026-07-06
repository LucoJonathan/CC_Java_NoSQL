package com.jonathanluco.doctorapp.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe représentant un Patient.
 * MongoDB collection: "patients"
 *
 * Un Patient hérite de User et représente un patient du système médical.
 * Le numéro de sécurité sociale (numeroSS) est unique mais pas l'ID primaire MongoDB.
 * L'ID primaire MongoDB est hérité de User.
 *
 * @author Jonathan Luco
 * @version 1.0
 */
@Document(collection = "patients")
public class Patient extends User {

    /**
     * Numéro de sécurité sociale du patient - Unique mais pas @Id.
     */
    @NotBlank
    @Indexed(unique = true)
    private String numeroSS;

    /**
     * Nom complet du patient.
     */
    @NotBlank
    private String nomPatient;

    public Patient() {
        super();
    }

    public Patient(String numeroSS, String nomPatient, String username, String password) {
        super();
        this.numeroSS = numeroSS;
        this.nomPatient = nomPatient;
        this.setUsername(username);
        this.setPassword(password);
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
}
