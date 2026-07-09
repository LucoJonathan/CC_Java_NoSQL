package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.service.IPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/patients")
@Tag(name = "Patients", description = "Endpoints pour la gestion des patients")
public class PatientController {

    @Autowired
    private IPatientService patientService;

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
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        return new ResponseEntity<>(patientService.createPatient(patientDTO), HttpStatus.CREATED);
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
    public ResponseEntity<PatientDTO> getPatientByNumeroSS(@PathVariable String numeroSS) {
        Optional<PatientDTO> patient = patientService.getPatientByNumeroSS(numeroSS);
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
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/page")
    @Operation(summary = "Récupérer une page de patients")
    @ApiResponse(responseCode = "200", description = "Page de patients")
    public ResponseEntity<Page<PatientDTO>> getPatientsPage(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(patientService.getPatientsPage(page, size));
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
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable String numeroSS,
                                                    @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(numeroSS, patientDTO);
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
