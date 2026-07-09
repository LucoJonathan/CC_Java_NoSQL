package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Contrat service medicament.
 */
public interface IMedicamentService {
    /**
     * Cree medicament.
     */
    MedicamentDTO createMedicament(MedicamentDTO medicamentDTO);

    /**
     * Cherche medicament par code.
     */
    Optional<MedicamentDTO> getMedicamentByCode(String code);

    /**
     * Cherche medicament par id.
     */
    Optional<MedicamentDTO> getMedicamentById(String id);

    /**
     * Liste medicaments.
     */
    List<MedicamentDTO> getAllMedicaments();

    /**
     * Met a jour medicament.
     */
    MedicamentDTO updateMedicament(String code, MedicamentDTO medicamentDetails);

    /**
     * Supprime medicament.
     */
    boolean deleteMedicament(String code);
}
