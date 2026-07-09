package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Contrat service medecin.
 */
public interface IMedecinService {
    /**
     * Cree medecin.
     */
    MedecinDTO createMedecin(MedecinDTO medecinDTO);

    /**
     * Cherche medecin par matricule.
     */
    Optional<MedecinDTO> getMedecinByMatricule(String matricule);

    /**
     * Cherche medecin par id.
     */
    Optional<MedecinDTO> getMedecinById(String id);

    /**
     * Liste medecins.
     */
    List<MedecinDTO> getAllMedecins();

    /**
     * Page medecins.
     */
    Page<MedecinDTO> getMedecinsPage(int page, int size);

    /**
     * Met a jour medecin.
     */
    MedecinDTO updateMedecin(String matricule, MedecinDTO medecinDetails);

    /**
     * Supprime medecin.
     */
    boolean deleteMedecin(String matricule);
}
