package com.jonathanluco.doctorapp.service;

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

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientByNumeroSS(String numeroSS) {
        return Optional.ofNullable(patientRepository.findByNumeroSS(numeroSS));
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(String numeroSS, Patient patientDetails) {
        Patient patient = patientRepository.findByNumeroSS(numeroSS);
        if (patient != null) {
            patient.setNomPatient(patientDetails.getNomPatient());
            patient.setUsername(patientDetails.getUsername());
            patient.setPassword(patientDetails.getPassword());
            return patientRepository.save(patient);
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
