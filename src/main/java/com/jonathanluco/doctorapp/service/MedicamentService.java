package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Medicament;
import com.jonathanluco.doctorapp.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    public Medicament createMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public Optional<Medicament> getMedicamentByCode(String code) {
        return Optional.ofNullable(medicamentRepository.findByCode(code));
    }

    public Optional<Medicament> getMedicamentById(String id) {
        return medicamentRepository.findById(id);
    }

    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    public Medicament updateMedicament(String code, Medicament medicamentDetails) {
        Medicament medicament = medicamentRepository.findByCode(code);
        if (medicament != null) {
            medicament.setLibelle(medicamentDetails.getLibelle());
            return medicamentRepository.save(medicament);
        }
        return null;
    }

    public boolean deleteMedicament(String code) {
        Medicament medicament = medicamentRepository.findByCode(code);
        if (medicament != null) {
            medicamentRepository.delete(medicament);
            return true;
        }
        return false;
    }
}
