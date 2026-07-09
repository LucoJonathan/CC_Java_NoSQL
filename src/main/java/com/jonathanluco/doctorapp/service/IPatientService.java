package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Contrat service patient.
 */
public interface IPatientService {
    /**
     * Cree patient.
     */
    PatientDTO createPatient(PatientDTO patientDTO);

    /**
     * Cherche patient par numero SS.
     */
    Optional<PatientDTO> getPatientByNumeroSS(String numeroSS);

    /**
     * Cherche patient par id.
     */
    Optional<PatientDTO> getPatientById(String id);

    /**
     * Liste patients.
     */
    List<PatientDTO> getAllPatients();

    /**
     * Page patients.
     */
    Page<PatientDTO> getPatientsPage(int page, int size);

    /**
     * Met a jour patient.
     */
    PatientDTO updatePatient(String numeroSS, PatientDTO patientDetails);

    /**
     * Supprime patient.
     */
    boolean deletePatient(String numeroSS);
}
