package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.service.IMedicamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controleur REST pour la gestion des medicaments.
 */
@RestController
@RequestMapping("/api/medicaments")
@PreAuthorize("hasRole('MEDECIN')")
public class MedicamentController {

    @Autowired
    private IMedicamentService medicamentService;

    @PostMapping
    public ResponseEntity<MedicamentDTO> createMedicament(@Valid @RequestBody MedicamentDTO medicamentDTO) {
        return ResponseEntity.ok(medicamentService.createMedicament(medicamentDTO));
    }

    @GetMapping("/{code}")
    public ResponseEntity<MedicamentDTO> getMedicamentByCode(@PathVariable String code) {
        Optional<MedicamentDTO> medicament = medicamentService.getMedicamentByCode(code);
        return medicament.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedicamentDTO>> getAllMedicaments() {
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @PutMapping("/{code}")
    public ResponseEntity<MedicamentDTO> updateMedicament(@PathVariable String code, @Valid @RequestBody MedicamentDTO medicamentDetails) {
        MedicamentDTO updatedMedicament = medicamentService.updateMedicament(code, medicamentDetails);
        return updatedMedicament != null ? ResponseEntity.ok(updatedMedicament) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable String code) {
        return medicamentService.deleteMedicament(code) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
