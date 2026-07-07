package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.exception.DuplicateResourceException;
import com.jonathanluco.doctorapp.mapper.PatientMapper;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PatientDTO createPatient(PatientDTO patientDTO) {
        ensureEmailIsAvailable(patientDTO.getEmail(), null);
        Patient patient = patientMapper.toModel(patientDTO);
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public Optional<PatientDTO> getPatientByNumeroSS(String numeroSS) {
        return Optional.ofNullable(patientRepository.findByNumeroSS(numeroSS))
                .map(patientMapper::toDto);
    }

    public Optional<PatientDTO> getPatientById(String id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto);
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

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
        if (existingPatient != null && !Objects.equals(existingPatient.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe déjà");
        }
        Medecin existingMedecin = medecinRepository.findByEmail(email);
        if (existingMedecin != null && !Objects.equals(existingMedecin.getId(), currentId)) {
            throw new DuplicateResourceException("Cet email existe déjà");
        }
    }
}
