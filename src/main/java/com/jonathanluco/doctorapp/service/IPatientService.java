package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    PatientDTO createPatient(PatientDTO patientDTO);
    Optional<PatientDTO> getPatientByNumeroSS(String numeroSS);
    Optional<PatientDTO> getPatientById(String id);
    List<PatientDTO> getAllPatients();
    Page<PatientDTO> getPatientsPage(int page, int size);
    PatientDTO updatePatient(String numeroSS, PatientDTO patientDetails);
    boolean deletePatient(String numeroSS);
}
