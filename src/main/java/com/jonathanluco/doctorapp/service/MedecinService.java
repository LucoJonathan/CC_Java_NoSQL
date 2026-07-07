package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.mapper.MedecinMapper;
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

    @Autowired
    private MedecinMapper medecinMapper;

    public MedecinDTO createMedecin(MedecinDTO medecinDTO) {
        Medecin medecin = medecinMapper.toModel(medecinDTO);
        return medecinMapper.toDto(medecinRepository.save(medecin));
    }

    public Optional<MedecinDTO> getMedecinByMatricule(String matricule) {
        return Optional.ofNullable(medecinRepository.findByMatricule(matricule))
                .map(medecinMapper::toDto);
    }

    public Optional<MedecinDTO> getMedecinById(String id) {
        return medecinRepository.findById(id)
                .map(medecinMapper::toDto);
    }

    public List<MedecinDTO> getAllMedecins() {
        return medecinRepository.findAll().stream()
                .map(medecinMapper::toDto)
                .toList();
    }

    public MedecinDTO updateMedecin(String matricule, MedecinDTO medecinDetails) {
        Medecin medecin = medecinRepository.findByMatricule(matricule);
        if (medecin != null) {
            medecinMapper.updateModel(medecin, medecinDetails);
            return medecinMapper.toDto(medecinRepository.save(medecin));
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
