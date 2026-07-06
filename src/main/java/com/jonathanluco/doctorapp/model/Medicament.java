package com.jonathanluco.doctorapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe représentant un Médicament.
 * MongoDB collection: "medicaments"
 *
 * Cette classe encapsule les informations d'un médicament disponible
 * pour les prescriptions dans les consultations.
 *
 * @author Jonathan Luco
 * @version 1.0
 */
@Document(collection = "medicaments")
public class Medicament {

    /**
     * Code unique du médicament - Clé primaire.
     */
    @Id
    private String code;

    /**
     * Libellé/Nom commercial du médicament.
     */
    @NotBlank
    private String libelle;

    public Medicament() {
    }

    public Medicament(String code, String libelle) {
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
