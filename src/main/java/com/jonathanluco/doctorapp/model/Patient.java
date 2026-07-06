package com.jonathanluco.doctorapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "patients")
public class Patient extends User {

    @Id
    private String numeroSS;

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
