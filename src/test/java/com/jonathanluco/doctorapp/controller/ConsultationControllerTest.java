package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.ConsultationDTO;
import com.jonathanluco.doctorapp.dto.PrescriptionDTO;
import com.jonathanluco.doctorapp.service.IConsultationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConsultationController Tests")
class ConsultationControllerTest {

    @Mock
    private IConsultationService consultationService;

    @InjectMocks
    private ConsultationController controller;

    @Test
    void createShouldReturnOk() {
        ConsultationDTO dto = new ConsultationDTO("C1", LocalDateTime.now());
        when(consultationService.createConsultation(dto)).thenReturn(dto);

        assertEquals(200, controller.createConsultation(dto).getStatusCode().value());
    }

    @Test
    void getByNumeroShouldReturnNotFound() {
        when(consultationService.getConsultationByNumeroConsultation(anyString())).thenReturn(Optional.empty());

        assertEquals(404, controller.getConsultationByNumeroConsultation("x").getStatusCode().value());
    }

    @Test
    void getAllShouldReturnOk() {
        when(consultationService.getAllConsultations()).thenReturn(List.of());

        assertEquals(200, controller.getAllConsultations().getStatusCode().value());
    }

    @Test
    void updateShouldReturnNotFoundWhenNull() {
        when(consultationService.updateConsultation(anyString(), any(ConsultationDTO.class))).thenReturn(null);

        assertEquals(404, controller.updateConsultation("x", new ConsultationDTO()).getStatusCode().value());
    }

    @Test
    void addPrescriptionShouldReturnNotFoundWhenNull() {
        when(consultationService.addPrescriptionToConsultation(anyString(), any(PrescriptionDTO.class))).thenReturn(null);

        assertEquals(404, controller.addPrescription("x", new PrescriptionDTO()).getStatusCode().value());
    }
}
