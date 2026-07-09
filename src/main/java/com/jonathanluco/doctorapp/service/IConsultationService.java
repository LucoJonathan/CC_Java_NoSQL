package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.dto.PrescriptionDTO;

import java.util.List;
import java.util.Optional;

public interface IConsultationService {
    ConsultationDTO createConsultation(ConsultationDTO consultationDTO);
    Optional<ConsultationDTO> getConsultationByNumeroConsultation(String numeroConsultation);
    List<ConsultationDTO> getAllConsultations();
    List<ConsultationDTO> getConsultationsByPatient(String numeroSS);
    List<ConsultationDTO> getConsultationsByMedecin(String matricule);
    ConsultationDTO updateConsultation(String numeroConsultation, ConsultationDTO consultationDetails);
    boolean deleteConsultation(String numeroConsultation);
    ConsultationDTO addPrescriptionToConsultation(String numeroConsultation, PrescriptionDTO prescriptionDTO);
}
