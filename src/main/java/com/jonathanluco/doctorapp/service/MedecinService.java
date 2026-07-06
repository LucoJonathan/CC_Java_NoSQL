package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    public Medecin createMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    public Optional<Medecin> getMedecinByMatricule(String matricule) {
        return Optional.ofNullable(medecinRepository.findByMatricule(matricule));
    }

    public Optional<Medecin> getMedecinById(String id) {
        return medecinRepository.findById(id);
    }

    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    public Medecin updateMedecin(String matricule, Medecin medecinDetails) {
        Medecin medecin = medecinRepository.findByMatricule(matricule);
        if (medecin != null) {
            medecin.setNomMedecin(medecinDetails.getNomMedecin());
            medecin.setUsername(medecinDetails.getUsername());
            medecin.setPassword(medecinDetails.getPassword());
            return medecinRepository.save(medecin);
        }
        return null;
    }

    public boolean deleteMedecin(String matricule) {
        Medecin medecin = medecinRepository.findByMatricule(matricule);
        if (medecin != null) {
            medecinRepository.delete(medecin);
            return true;
        }
        return false;
    }
}
