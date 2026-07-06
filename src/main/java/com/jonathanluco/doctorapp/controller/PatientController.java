package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @GetMapping("/{numeroSS}")
    public ResponseEntity<Patient> getPatientByNumeroSS(@PathVariable String numeroSS) {
        Optional<Patient> patient = patientService.getPatientByNumeroSS(numeroSS);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PutMapping("/{numeroSS}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String numeroSS, @RequestBody Patient patientDetails) {
        Patient updatedPatient = patientService.updatePatient(numeroSS, patientDetails);
        return updatedPatient != null ? ResponseEntity.ok(updatedPatient) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{numeroSS}")
    public ResponseEntity<Void> deletePatient(@PathVariable String numeroSS) {
        return patientService.deletePatient(numeroSS) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
