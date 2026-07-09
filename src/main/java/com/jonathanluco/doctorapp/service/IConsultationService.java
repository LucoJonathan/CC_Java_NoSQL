package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.dto.PrescriptionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Contrat service consultation.
 */
public interface IConsultationService {
    /**
     * Cree consultation.
     */
    ConsultationDTO createConsultation(ConsultationDTO consultationDTO);

    /**
     * Cherche consultation par numero.
     */
    Optional<ConsultationDTO> getConsultationByNumeroConsultation(String numeroConsultation);

    /**
     * Liste consultations.
     */
    List<ConsultationDTO> getAllConsultations();

    /**
     * Liste consultations patient.
     */
    List<ConsultationDTO> getConsultationsByPatient(String numeroSS);

    /**
     * Liste consultations medecin.
     */
    List<ConsultationDTO> getConsultationsByMedecin(String matricule);

    /**
     * Met a jour consultation.
     */
    ConsultationDTO updateConsultation(String numeroConsultation, ConsultationDTO consultationDetails);

    /**
     * Supprime consultation.
     */
    boolean deleteConsultation(String numeroConsultation);

    /**
     * Ajoute prescription.
     */
    ConsultationDTO addPrescriptionToConsultation(String numeroConsultation, PrescriptionDTO prescriptionDTO);
}
