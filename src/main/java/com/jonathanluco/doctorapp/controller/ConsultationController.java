package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.dto.PrescriptionDTO;
import com.jonathanluco.doctorapp.service.IConsultationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controleur REST pour la gestion des consultations.
 *
 * Acces reserve au role MEDECIN, sauf la recuperation d'une consultation
 * par son numero qui est aussi accessible au role PATIENT.
 */
@RestController
@RequestMapping("/api/consultations")
@PreAuthorize("hasRole('MEDECIN')")
public class ConsultationController {

    @Autowired
    private IConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationDTO> createConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) {
        return ResponseEntity.ok(consultationService.createConsultation(consultationDTO));
    }

    @GetMapping("/{numeroConsultation}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'PATIENT')")
    public ResponseEntity<ConsultationDTO> getConsultationByNumeroConsultation(@PathVariable String numeroConsultation) {
        Optional<ConsultationDTO> consultation = consultationService.getConsultationByNumeroConsultation(numeroConsultation);
        return consultation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @GetMapping("/patient/{numeroSS}")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByPatient(@PathVariable String numeroSS) {
        return ResponseEntity.ok(consultationService.getConsultationsByPatient(numeroSS));
    }

    @GetMapping("/medecin/{matricule}")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByMedecin(@PathVariable String matricule) {
        return ResponseEntity.ok(consultationService.getConsultationsByMedecin(matricule));
    }

    @PutMapping("/{numeroConsultation}")
    public ResponseEntity<ConsultationDTO> updateConsultation(@PathVariable String numeroConsultation,
                                                              @Valid @RequestBody ConsultationDTO consultationDetails) {
        ConsultationDTO updatedConsultation = consultationService.updateConsultation(numeroConsultation, consultationDetails);
        return updatedConsultation != null ? ResponseEntity.ok(updatedConsultation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numeroConsultation}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable String numeroConsultation) {
        return consultationService.deleteConsultation(numeroConsultation) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{numeroConsultation}/prescriptions")
    public ResponseEntity<ConsultationDTO> addPrescription(@PathVariable String numeroConsultation,
                                                           @Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        ConsultationDTO updatedConsultation = consultationService.addPrescriptionToConsultation(numeroConsultation, prescriptionDTO);
        return updatedConsultation != null ? ResponseEntity.ok(updatedConsultation) : ResponseEntity.notFound().build();
    }
}
