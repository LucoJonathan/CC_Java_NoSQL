package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.dto.PrescriptionDTO;
import com.jonathanluco.doctorapp.exception.ResourceNotFoundException;
import com.jonathanluco.doctorapp.mapper.ConsultationMapper;
import com.jonathanluco.doctorapp.mapper.PrescriptionMapper;
import com.jonathanluco.doctorapp.model.Consultation;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.repository.ConsultationRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    public ConsultationDTO createConsultation(ConsultationDTO consultationDTO) {
        Patient patient = findPatientByNumeroSS(consultationDTO.getPatientNumeroSS());
        Medecin medecin = findMedecinByMatricule(consultationDTO.getMedecinMatricule());
        Consultation consultation = consultationMapper.toModel(consultationDTO, patient, medecin);
        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    public Optional<ConsultationDTO> getConsultationById(String numeroConsultation) {
        return consultationRepository.findById(numeroConsultation)
                .map(consultationMapper::toDto);
    }

    public List<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto)
                .toList();
    }

    public List<ConsultationDTO> getConsultationsByPatient(String numeroSS) {
        return consultationRepository.findByPatientAssiste_NumeroSS(numeroSS).stream()
                .map(consultationMapper::toDto)
                .toList();
    }

    public List<ConsultationDTO> getConsultationsByMedecin(String matricule) {
        return consultationRepository.findByMedecinDonne_Matricule(matricule).stream()
                .map(consultationMapper::toDto)
                .toList();
    }

    public ConsultationDTO updateConsultation(String numeroConsultation, ConsultationDTO consultationDetails) {
        Optional<Consultation> existingConsultation = consultationRepository.findById(numeroConsultation);
        if (existingConsultation.isPresent()) {
            Patient patient = findPatientByNumeroSS(consultationDetails.getPatientNumeroSS());
            Medecin medecin = findMedecinByMatricule(consultationDetails.getMedecinMatricule());
            Consultation consultation = existingConsultation.get();
            consultationMapper.updateModel(consultationDetails, patient, medecin, consultation);
            return consultationMapper.toDto(consultationRepository.save(consultation));
        }
        return null;
    }

    public boolean deleteConsultation(String numeroConsultation) {
        if (consultationRepository.existsById(numeroConsultation)) {
            consultationRepository.deleteById(numeroConsultation);
            return true;
        }
        return false;
    }

    public ConsultationDTO addPrescriptionToConsultation(String numeroConsultation, PrescriptionDTO prescriptionDTO) {
        Optional<Consultation> consultation = consultationRepository.findById(numeroConsultation);
        if (consultation.isPresent()) {
            consultation.get().addPrescription(prescriptionMapper.toModel(prescriptionDTO));
            return consultationMapper.toDto(consultationRepository.save(consultation.get()));
        }
        return null;
    }

    private Patient findPatientByNumeroSS(String numeroSS) {
        if (numeroSS == null || numeroSS.isBlank()) {
            return null;
        }
        Patient patient = patientRepository.findByNumeroSS(numeroSS);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient", "numeroSS", numeroSS);
        }
        return patient;
    }

    private Medecin findMedecinByMatricule(String matricule) {
        if (matricule == null || matricule.isBlank()) {
            return null;
        }
        Medecin medecin = medecinRepository.findByMatricule(matricule);
        if (medecin == null) {
            throw new ResourceNotFoundException("Medecin", "matricule", matricule);
        }
        return medecin;
    }
}
