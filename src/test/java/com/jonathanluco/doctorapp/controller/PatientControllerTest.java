package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.service.IPatientService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PatientController Tests")
class PatientControllerTest {

    @Mock
    private IPatientService patientService;

    @InjectMocks
    private PatientController controller;

    @Test
    void createShouldReturnCreated() {
        PatientDTO dto = new PatientDTO("123", "Jean", "jean", "j@example.com", "Password1!");
        when(patientService.createPatient(dto)).thenReturn(dto);

        assertEquals(201, controller.createPatient(dto).getStatusCode().value());
    }

    @Test
    void getByNumeroSSShouldReturnNotFound() {
        when(patientService.getPatientByNumeroSS(anyString())).thenReturn(Optional.empty());

        assertEquals(404, controller.getPatientByNumeroSS("x").getStatusCode().value());
    }

    @Test
    void getAllShouldReturnOk() {
        when(patientService.getAllPatients()).thenReturn(List.of());

        assertEquals(200, controller.getAllPatients().getStatusCode().value());
    }

    @Test
    void pageShouldReturnOk() {
        when(patientService.getPatientsPage(anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        assertEquals(200, controller.getPatientsPage(0, 10).getStatusCode().value());
    }

    @Test
    void updateShouldReturnNotFoundWhenNull() {
        when(patientService.updatePatient(anyString(), any(PatientDTO.class))).thenReturn(null);

        assertEquals(404, controller.updatePatient("123", new PatientDTO()).getStatusCode().value());
    }

    @Test
    void deleteShouldReturnNoContent() {
        when(patientService.deletePatient("123")).thenReturn(true);

        assertEquals(204, controller.deletePatient("123").getStatusCode().value());
    }
}
