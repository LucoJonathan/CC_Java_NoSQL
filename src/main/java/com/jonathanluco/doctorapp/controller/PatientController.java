package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur REST pour les opérations sur les Patients.
 * Expose les endpoints pour la gestion CRUD des patients.
 */
@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patients", description = "Endpoints pour la gestion des patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Crée un nouveau patient.
     *
     * @param patientDTO les données du patient
     * @return la réponse avec le patient créé
     */
    @PostMapping
    @Operation(summary = "Créer un patient", description = "Crée un nouveau patient dans le système")
    @ApiResponse(responseCode = "201", description = "Patient créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient(patientDTO.getNumeroSS(), patientDTO.getNomPatient(),
                patientDTO.getUsername(), patientDTO.getPassword());
        return new ResponseEntity<>(patientService.createPatient(patient), HttpStatus.CREATED);
    }

    /**
     * Récupère un patient par son numéro SS.
     *
     * @param numeroSS le numéro de sécurité sociale
     * @return le patient trouvé ou 404
     */
    @GetMapping("/{numeroSS}")
    @Operation(summary = "Récupérer un patient", description = "Récupère les détails d'un patient par son numéro SS")
    @ApiResponse(responseCode = "200", description = "Patient trouvé")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<Patient> getPatientByNumeroSS(@PathVariable String numeroSS) {
        Optional<Patient> patient = patientService.getPatientByNumeroSS(numeroSS);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Récupère tous les patients.
     *
     * @return la liste de tous les patients
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les patients")
    @ApiResponse(responseCode = "200", description = "Liste des patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    /**
     * Met à jour un patient existant.
     *
     * @param numeroSS le numéro SS du patient à mettre à jour
     * @param patientDTO les nouvelles données
     * @return le patient mis à jour
     */
    @PutMapping("/{numeroSS}")
    @Operation(summary = "Mettre à jour un patient")
    @ApiResponse(responseCode = "200", description = "Patient mis à jour")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<Patient> updatePatient(@PathVariable String numeroSS,
                                                 @Valid @RequestBody PatientDTO patientDTO) {
        Patient patientDetails = new Patient(patientDTO.getNumeroSS(), patientDTO.getNomPatient(),
                patientDTO.getUsername(), patientDTO.getPassword());
        Patient updatedPatient = patientService.updatePatient(numeroSS, patientDetails);
        return updatedPatient != null ? ResponseEntity.ok(updatedPatient) : ResponseEntity.notFound().build();
    }

    /**
     * Supprime un patient.
     *
     * @param numeroSS le numéro SS du patient à supprimer
     * @return 204 No Content si succès
     */
    @DeleteMapping("/{numeroSS}")
    @Operation(summary = "Supprimer un patient")
    @ApiResponse(responseCode = "204", description = "Patient supprimé")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<Void> deletePatient(@PathVariable String numeroSS) {
        return patientService.deletePatient(numeroSS) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
