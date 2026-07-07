package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.service.MedecinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;

    @PostMapping
    public ResponseEntity<MedecinDTO> createMedecin(@Valid @RequestBody MedecinDTO medecinDTO) {
        return ResponseEntity.ok(medecinService.createMedecin(medecinDTO));
    }

    @GetMapping("/{matricule}")
    public ResponseEntity<MedecinDTO> getMedecinByMatricule(@PathVariable String matricule) {
        Optional<MedecinDTO> medecin = medecinService.getMedecinByMatricule(matricule);
        return medecin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MedecinDTO>> getAllMedecins() {
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    @PutMapping("/{matricule}")
    public ResponseEntity<MedecinDTO> updateMedecin(@PathVariable String matricule, @Valid @RequestBody MedecinDTO medecinDetails) {
        MedecinDTO updatedMedecin = medecinService.updateMedecin(matricule, medecinDetails);
        return updatedMedecin != null ? ResponseEntity.ok(updatedMedecin) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{matricule}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable String matricule) {
        return medecinService.deleteMedecin(matricule) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
