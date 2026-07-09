package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.model.Medicament;
import com.jonathanluco.doctorapp.repository.MedicamentRepository;
import com.jonathanluco.doctorapp.service.impl.MedicamentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MedicamentService Tests")
class MedicamentServiceTest {

    @Mock
    private MedicamentRepository medicamentRepository;

    @Mock
    private com.jonathanluco.doctorapp.mapper.MedicamentMapper medicamentMapper;

    @InjectMocks
    private MedicamentServiceImpl medicamentService;

    @Test
    void createShouldSaveMedicament() {
        MedicamentDTO dto = new MedicamentDTO("MED001", "Aspirin");
        Medicament medicament = new Medicament("MED001", "Aspirin");
        when(medicamentMapper.toModel(dto)).thenReturn(medicament);
        when(medicamentRepository.save(any(Medicament.class))).thenReturn(medicament);
        when(medicamentMapper.toDto(medicament)).thenReturn(dto);

        assertEquals("MED001", medicamentService.createMedicament(dto).getCode());
    }

    @Test
    void getByCodeShouldReturnOptional() {
        Medicament medicament = new Medicament("MED001", "Aspirin");
        MedicamentDTO dto = new MedicamentDTO("MED001", "Aspirin");
        when(medicamentRepository.findByCode("MED001")).thenReturn(medicament);
        when(medicamentMapper.toDto(medicament)).thenReturn(dto);

        assertTrue(medicamentService.getMedicamentByCode("MED001").isPresent());
    }

    @Test
    void getAllShouldMapAll() {
        Medicament medicament = new Medicament("MED001", "Aspirin");
        MedicamentDTO dto = new MedicamentDTO("MED001", "Aspirin");
        when(medicamentRepository.findAll()).thenReturn(List.of(medicament));
        when(medicamentMapper.toDto(medicament)).thenReturn(dto);

        assertEquals(1, medicamentService.getAllMedicaments().size());
    }

    @Test
    void updateShouldReturnNullIfMissing() {
        assertNull(medicamentService.updateMedicament("X", new MedicamentDTO("X", "Y")));
    }

    @Test
    void deleteShouldReturnFalseIfMissing() {
        assertFalse(medicamentService.deleteMedicament("X"));
    }
}
