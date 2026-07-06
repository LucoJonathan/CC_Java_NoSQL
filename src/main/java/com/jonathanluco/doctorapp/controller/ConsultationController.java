package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.model.Consultation;
import com.jonathanluco.doctorapp.model.Prescription;
import com.jonathanluco.doctorapp.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) {
        return ResponseEntity.ok(consultationService.createConsultation(consultation));
    }

    @GetMapping("/{numeroConsultation}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable String numeroConsultation) {
        Optional<Consultation> consultation = consultationService.getConsultationById(numeroConsultation);
        return consultation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @GetMapping("/patient/{numeroSS}")
    public ResponseEntity<List<Consultation>> getConsultationsByPatient(@PathVariable String numeroSS) {
        return ResponseEntity.ok(consultationService.getConsultationsByPatient(numeroSS));
    }

    @GetMapping("/medecin/{matricule}")
    public ResponseEntity<List<Consultation>> getConsultationsByMedecin(@PathVariable String matricule) {
        return ResponseEntity.ok(consultationService.getConsultationsByMedecin(matricule));
    }

    @PutMapping("/{numeroConsultation}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable String numeroConsultation, @RequestBody Consultation consultationDetails) {
        Consultation updatedConsultation = consultationService.updateConsultation(numeroConsultation, consultationDetails);
        return updatedConsultation != null ? ResponseEntity.ok(updatedConsultation) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numeroConsultation}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable String numeroConsultation) {
        return consultationService.deleteConsultation(numeroConsultation) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{numeroConsultation}/prescriptions")
    public ResponseEntity<Consultation> addPrescription(@PathVariable String numeroConsultation, @RequestBody Prescription prescription) {
        Consultation updatedConsultation = consultationService.addPrescriptionToConsultation(numeroConsultation, prescription);
        return updatedConsultation != null ? ResponseEntity.ok(updatedConsultation) : ResponseEntity.notFound().build();
    }
}
