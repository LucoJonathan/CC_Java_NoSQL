package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.service.IMedecinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MedecinController Tests")
class MedecinControllerTest {

    @Mock
    private IMedecinService medecinService;

    @InjectMocks
    private MedecinController controller;

    @Test
    void createShouldReturnOk() {
        MedecinDTO dto = new MedecinDTO("DOC001", "Dr", "dr", "dr@example.com", "Password1!");
        when(medecinService.createMedecin(dto)).thenReturn(dto);

        assertEquals(200, controller.createMedecin(dto).getStatusCode().value());
    }

    @Test
    void getByMatriculeShouldReturnNotFound() {
        when(medecinService.getMedecinByMatricule(anyString())).thenReturn(Optional.empty());

        assertEquals(404, controller.getMedecinByMatricule("x").getStatusCode().value());
    }

    @Test
    void getAllShouldReturnList() {
        when(medecinService.getAllMedecins()).thenReturn(List.of());

        assertEquals(200, controller.getAllMedecins().getStatusCode().value());
    }

    @Test
    void pageShouldReturnPage() {
        when(medecinService.getMedecinsPage(anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        assertEquals(200, controller.getMedecinsPage(0, 10).getStatusCode().value());
    }

    @Test
    void updateShouldReturnNotFoundWhenNull() {
        when(medecinService.updateMedecin(anyString(), any(MedecinDTO.class))).thenReturn(null);

        assertEquals(404, controller.updateMedecin("DOC001", new MedecinDTO()).getStatusCode().value());
    }

    @Test
    void deleteShouldReturnNoContent() {
        when(medecinService.deleteMedecin("DOC001")).thenReturn(true);

        assertEquals(204, controller.deleteMedecin("DOC001").getStatusCode().value());
    }
}
