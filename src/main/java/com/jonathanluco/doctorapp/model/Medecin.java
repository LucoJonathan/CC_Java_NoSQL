package com.jonathanluco.doctorapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "medecins")
public class Medecin extends User {

    @Id
    private String matricule;

    @NotBlank
    private String nomMedecin;

    public Medecin() {
        super();
    }

    public Medecin(String matricule, String nomMedecin, String username, String password) {
        super();
        this.matricule = matricule;
        this.nomMedecin = nomMedecin;
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
}
