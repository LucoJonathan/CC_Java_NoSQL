package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.mapper.MedicamentMapper;
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

    @Autowired
    private MedicamentMapper medicamentMapper;

    public MedicamentDTO createMedicament(MedicamentDTO medicamentDTO) {
        Medicament medicament = medicamentMapper.toModel(medicamentDTO);
        return medicamentMapper.toDto(medicamentRepository.save(medicament));
    }

    public Optional<MedicamentDTO> getMedicamentByCode(String code) {
        return Optional.ofNullable(medicamentRepository.findByCode(code))
                .map(medicamentMapper::toDto);
    }

    public Optional<MedicamentDTO> getMedicamentById(String id) {
        return medicamentRepository.findById(id)
                .map(medicamentMapper::toDto);
    }

    public List<MedicamentDTO> getAllMedicaments() {
        return medicamentRepository.findAll().stream()
                .map(medicamentMapper::toDto)
                .toList();
    }

    public MedicamentDTO updateMedicament(String code, MedicamentDTO medicamentDetails) {
        Medicament medicament = medicamentRepository.findByCode(code);
        if (medicament != null) {
            medicamentMapper.updateModel(medicament, medicamentDetails);
            return medicamentMapper.toDto(medicamentRepository.save(medicament));
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
