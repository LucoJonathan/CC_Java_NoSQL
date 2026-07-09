package com.jonathanluco.doctorapp.service.impl;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.mapper.MedicamentMapper;
import com.jonathanluco.doctorapp.model.Medicament;
import com.jonathanluco.doctorapp.repository.MedicamentRepository;
import com.jonathanluco.doctorapp.service.IMedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation service medicament.
 */
@Service
public class MedicamentServiceImpl implements IMedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private MedicamentMapper medicamentMapper;

    /**
     * Cree medicament.
     */
    @Override
    public MedicamentDTO createMedicament(MedicamentDTO medicamentDTO) {
        Medicament medicament = medicamentMapper.toModel(medicamentDTO);
        return medicamentMapper.toDto(medicamentRepository.save(medicament));
    }

    /**
     * Cherche medicament par code.
     */
    @Override
    public Optional<MedicamentDTO> getMedicamentByCode(String code) {
        return Optional.ofNullable(medicamentRepository.findByCode(code))
                .map(medicamentMapper::toDto);
    }

    /**
     * Cherche medicament par id.
     */
    @Override
    public Optional<MedicamentDTO> getMedicamentById(String id) {
        return medicamentRepository.findById(id)
                .map(medicamentMapper::toDto);
    }

    /**
     * Retourne tous medicaments.
     */
    @Override
    public List<MedicamentDTO> getAllMedicaments() {
        return medicamentRepository.findAll().stream()
                .map(medicamentMapper::toDto)
                .toList();
    }

    /**
     * Met a jour medicament.
     */
    @Override
    public MedicamentDTO updateMedicament(String code, MedicamentDTO medicamentDetails) {
        Medicament medicament = medicamentRepository.findByCode(code);
        if (medicament != null) {
            medicamentMapper.updateModel(medicament, medicamentDetails);
            return medicamentMapper.toDto(medicamentRepository.save(medicament));
        }
        return null;
    }

    /**
     * Supprime medicament.
     */
    @Override
    public boolean deleteMedicament(String code) {
        Medicament medicament = medicamentRepository.findByCode(code);
        if (medicament != null) {
            medicamentRepository.delete(medicament);
            return true;
        }
        return false;
    }
}
