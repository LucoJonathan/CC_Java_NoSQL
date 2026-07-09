package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;

import java.util.List;
import java.util.Optional;

public interface IMedicamentService {
    MedicamentDTO createMedicament(MedicamentDTO medicamentDTO);
    Optional<MedicamentDTO> getMedicamentByCode(String code);
    Optional<MedicamentDTO> getMedicamentById(String id);
    List<MedicamentDTO> getAllMedicaments();
    MedicamentDTO updateMedicament(String code, MedicamentDTO medicamentDetails);
    boolean deleteMedicament(String code);
}
