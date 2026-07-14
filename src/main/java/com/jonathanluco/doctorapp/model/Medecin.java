package com.jonathanluco.doctorapp.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe représentant un Médecin.
 * MongoDB collection: "medecins"
 *
 * Un Médecin hérite de User et représente un professionnel de santé.
 * Le matricule est unique mais pas l'ID primaire MongoDB.
 * L'ID primaire MongoDB est hérité de User.
 *
 */
@Document(collection = "medecins")
public class Medecin extends User {

    /**
     * Matricule du médecin - Unique mais pas @Id.
     */
    @NotBlank
    @Indexed(unique = true)
    private String matricule;

    /**
     * Nom complet du médecin.
     */
    @NotBlank
    private String nomMedecin;

    public Medecin() {
        super();
    }

    public Medecin(String matricule, String nomMedecin, String email, String username, String password) {
        super();
        this.matricule = matricule;
        this.nomMedecin = nomMedecin;
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
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

    @Override
    public Role getRole() {
        return Role.MEDECIN;
    }
}
