package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.mapper.PatientMapper;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toModel(patientDTO);
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
            patientMapper.updateModel(patient, patientDetails);
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
}
