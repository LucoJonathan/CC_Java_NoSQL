package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.model.Medicament;
import com.jonathanluco.doctorapp.service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicaments")
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

    @PostMapping
    public ResponseEntity<Medicament> createMedicament(@RequestBody Medicament medicament) {
        return ResponseEntity.ok(medicamentService.createMedicament(medicament));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Medicament> getMedicamentByCode(@PathVariable String code) {
        Optional<Medicament> medicament = medicamentService.getMedicamentByCode(code);
        return medicament.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Medicament>> getAllMedicaments() {
        return ResponseEntity.ok(medicamentService.getAllMedicaments());
    }

    @PutMapping("/{code}")
    public ResponseEntity<Medicament> updateMedicament(@PathVariable String code, @RequestBody Medicament medicamentDetails) {
        Medicament updatedMedicament = medicamentService.updateMedicament(code, medicamentDetails);
        return updatedMedicament != null ? ResponseEntity.ok(updatedMedicament) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable String code) {
        return medicamentService.deleteMedicament(code) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
