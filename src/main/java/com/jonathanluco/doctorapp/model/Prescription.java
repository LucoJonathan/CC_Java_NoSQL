package com.jonathanluco.doctorapp.model;

/**
 * Classe d'association représentant une Prescription.
 *
 * Cette classe établit la relation Many-to-Many entre Consultation et Medicament,
 * avec un attribut supplémentaire "nbPrise" indiquant le nombre de prises prescrites.
 *
 */
public class Prescription {

    /**
     * Code du médicament prescrit.
     */
    private String codeMedicament;

    /**
     * Nombre de prises du médicament prescrit.
     */
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
