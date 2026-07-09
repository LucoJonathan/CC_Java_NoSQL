package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IMedecinService {
    MedecinDTO createMedecin(MedecinDTO medecinDTO);
    Optional<MedecinDTO> getMedecinByMatricule(String matricule);
    Optional<MedecinDTO> getMedecinById(String id);
    List<MedecinDTO> getAllMedecins();
    Page<MedecinDTO> getMedecinsPage(int page, int size);
    MedecinDTO updateMedecin(String matricule, MedecinDTO medecinDetails);
    boolean deleteMedecin(String matricule);
}
