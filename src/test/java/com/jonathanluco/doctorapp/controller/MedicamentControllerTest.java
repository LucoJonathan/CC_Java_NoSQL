package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.MedicamentDTO;
import com.jonathanluco.doctorapp.service.IMedicamentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MedicamentController Tests")
class MedicamentControllerTest {

    @Mock
    private IMedicamentService medicamentService;

    @InjectMocks
    private MedicamentController controller;

    @Test
    void createShouldReturnOk() {
        MedicamentDTO dto = new MedicamentDTO("MED001", "Aspirin");
        when(medicamentService.createMedicament(dto)).thenReturn(dto);

        assertEquals(200, controller.createMedicament(dto).getStatusCode().value());
    }

    @Test
    void getByCodeShouldReturnNotFound() {
        when(medicamentService.getMedicamentByCode(anyString())).thenReturn(Optional.empty());

        assertEquals(404, controller.getMedicamentByCode("x").getStatusCode().value());
    }

    @Test
    void getAllShouldReturnOk() {
        when(medicamentService.getAllMedicaments()).thenReturn(List.of());

        assertEquals(200, controller.getAllMedicaments().getStatusCode().value());
    }

    @Test
    void updateShouldReturnNotFoundWhenNull() {
        when(medicamentService.updateMedicament(anyString(), any(MedicamentDTO.class))).thenReturn(null);

        assertEquals(404, controller.updateMedicament("x", new MedicamentDTO()).getStatusCode().value());
    }

    @Test
    void deleteShouldReturnNoContent() {
        when(medicamentService.deleteMedicament("x")).thenReturn(true);

        assertEquals(204, controller.deleteMedicament("x").getStatusCode().value());
    }
}
