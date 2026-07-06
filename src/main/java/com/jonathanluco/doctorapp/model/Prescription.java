package com.jonathanluco.doctorapp.model;

public class Prescription {

    private String codeMedicament;
    private int nbPrise;

    public Prescription() {
    }

    public Prescription(String codeMedicament, int nbPrise) {
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
