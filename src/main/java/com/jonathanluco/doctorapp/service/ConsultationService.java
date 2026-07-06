package com.jonathanluco.doctorapp.service;

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

    public Consultation createConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public Optional<Consultation> getConsultationById(String numeroConsultation) {
        return consultationRepository.findById(numeroConsultation);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public List<Consultation> getConsultationsByPatient(String numeroSS) {
        return consultationRepository.findByPatientAssiste_NumeroSS(numeroSS);
    }

    public List<Consultation> getConsultationsByMedecin(String matricule) {
        return consultationRepository.findByMedecinDonne_Matricule(matricule);
    }

    public Consultation updateConsultation(String numeroConsultation, Consultation consultationDetails) {
        Optional<Consultation> existingConsultation = consultationRepository.findById(numeroConsultation);
        if (existingConsultation.isPresent()) {
            Consultation consultation = existingConsultation.get();
            consultation.setDate(consultationDetails.getDate());
            consultation.setPatientAssiste(consultationDetails.getPatientAssiste());
            consultation.setMedecinDonne(consultationDetails.getMedecinDonne());
            consultation.setPrescriptions(consultationDetails.getPrescriptions());
            return consultationRepository.save(consultation);
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

    public Consultation addPrescriptionToConsultation(String numeroConsultation, com.jonathanluco.doctorapp.model.Prescription prescription) {
        Optional<Consultation> consultation = consultationRepository.findById(numeroConsultation);
        if (consultation.isPresent()) {
            consultation.get().addPrescription(prescription);
            return consultationRepository.save(consultation.get());
        }
        return null;
    }
}
