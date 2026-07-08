package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.exception.DuplicateResourceException;
import com.jonathanluco.doctorapp.mapper.MedecinMapper;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service metier pour la gestion des medecins.
 */
@Service
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private MedecinMapper medecinMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MedecinDTO createMedecin(MedecinDTO medecinDTO) {
        ensureMatriculeIsAvailable(medecinDTO.getMatricule(), null);
        ensureEmailIsAvailable(medecinDTO.getEmail(), null);
        Medecin medecin = medecinMapper.toModel(medecinDTO);
        medecin.setPassword(passwordEncoder.encode(medecinDTO.getPassword()));
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

    public Page<MedecinDTO> getMedecinsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nomMedecin").ascending());
        return medecinMapper.toDtoPage(medecinRepository.findAllBy(pageable));
    }

    public MedecinDTO updateMedecin(String matricule, MedecinDTO medecinDetails) {
        Medecin medecin = medecinRepository.findByMatricule(matricule);
        if (medecin != null) {
            ensureEmailIsAvailable(medecinDetails.getEmail(), medecin.getId());
            medecinMapper.updateModel(medecin, medecinDetails);
            medecin.setPassword(passwordEncoder.encode(medecinDetails.getPassword()));
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

    private void ensureEmailIsAvailable(String email, String currentId) {
        Medecin existingMedecin = medecinRepository.findByEmail(email);
        if (existingMedecin != null && isDifferentEntity(existingMedecin.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe déjà");
        }
        Patient existingPatient = patientRepository.findByEmail(email);
        if (existingPatient != null && isDifferentEntity(existingPatient.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe déjà");
        }
    }

    private void ensureMatriculeIsAvailable(String matricule, String currentId) {
        Medecin existingMedecin = medecinRepository.findByMatricule(matricule);
        if (existingMedecin != null && isDifferentEntity(existingMedecin.getId(), currentId)) {
            throw new DuplicateResourceException("Ce matricule existe déjà");
        }
    }

    private boolean isDifferentEntity(String existingId, String currentId) {
        return currentId == null || !Objects.equals(existingId, currentId);
    }
}
