package com.jonathanluco.doctorapp.service.impl;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.exception.DuplicateResourceException;
import com.jonathanluco.doctorapp.mapper.PatientMapper;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.service.IPatientService;
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
 * Implementation service patient.
 */
@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cree patient avec numero SS unique.
     */
    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        ensureNumeroSSIsAvailable(patientDTO.getNumeroSS(), null);
        ensureEmailIsAvailable(patientDTO.getEmail(), null);
        Patient patient = patientMapper.toModel(patientDTO);
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        return patientMapper.toDto(patientRepository.save(patient));
    }

    /**
     * Cherche patient par numero SS.
     */
    @Override
    public Optional<PatientDTO> getPatientByNumeroSS(String numeroSS) {
        return Optional.ofNullable(patientRepository.findByNumeroSS(numeroSS))
                .map(patientMapper::toDto);
    }

    /**
     * Cherche patient par id.
     */
    @Override
    public Optional<PatientDTO> getPatientById(String id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto);
    }

    /**
     * Retourne tous patients.
     */
    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    /**
     * Retourne page de patients.
     */
    @Override
    public Page<PatientDTO> getPatientsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nomPatient").ascending());
        return patientMapper.toDtoPage(patientRepository.findAllBy(pageable));
    }

    /**
     * Met a jour patient existant.
     */
    @Override
    public PatientDTO updatePatient(String numeroSS, PatientDTO patientDetails) {
        Patient patient = patientRepository.findByNumeroSS(numeroSS);
        if (patient != null) {
            ensureEmailIsAvailable(patientDetails.getEmail(), patient.getId());
            patientMapper.updateModel(patient, patientDetails);
            patient.setPassword(passwordEncoder.encode(patientDetails.getPassword()));
            return patientMapper.toDto(patientRepository.save(patient));
        }
        return null;
    }

    /**
     * Supprime patient par numero SS.
     */
    @Override
    public boolean deletePatient(String numeroSS) {
        Patient patient = patientRepository.findByNumeroSS(numeroSS);
        if (patient != null) {
            patientRepository.delete(patient);
            return true;
        }
        return false;
    }

    private void ensureEmailIsAvailable(String email, String currentId) {
        Patient existingPatient = patientRepository.findByEmail(email);
        if (existingPatient != null && isDifferentEntity(existingPatient.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe deja");
        }
        Medecin existingMedecin = medecinRepository.findByEmail(email);
        if (existingMedecin != null && isDifferentEntity(existingMedecin.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe deja");
        }
    }

    private void ensureNumeroSSIsAvailable(String numeroSS, String currentId) {
        Patient existingPatient = patientRepository.findByNumeroSS(numeroSS);
        if (existingPatient != null && isDifferentEntity(existingPatient.getId(), currentId)) {
            throw new DuplicateResourceException("Ce numéro de sécurité sociale existe déjà");
        }
    }

    private boolean isDifferentEntity(String existingId, String currentId) {
        return currentId == null || !Objects.equals(existingId, currentId);
    }
}
