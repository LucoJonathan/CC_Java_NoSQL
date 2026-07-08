package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.MedecinDTO;
import com.jonathanluco.doctorapp.exception.DuplicateResourceException;
import com.jonathanluco.doctorapp.mapper.MedecinMapper;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires pour MedecinService.
 */
@DisplayName("MedecinService Tests")
class MedecinServiceTest {

    @Mock
    private MedecinRepository medecinRepository;

    @Mock
    private MedecinMapper medecinMapper;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MedecinService medecinService;

    private Medecin medecin;
    private MedecinDTO medecinDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        medecin = new Medecin("DOC001", "Dr Martin", "dr.martin@example.com", "dr_martin", "password123");
        medecinDTO = new MedecinDTO("DOC001", "Dr Martin", "dr_martin", "dr.martin@example.com", "password123");
    }

    @Test
    @DisplayName("Devrait récupérer une page de médecins")
    void testGetMedecinsPage() {
        Page<Medecin> medecinPage = new PageImpl<>(List.of(medecin), PageRequest.of(0, 1), 1);
        Page<MedecinDTO> medecinDtoPage = new PageImpl<>(List.of(medecinDTO), PageRequest.of(0, 1), 1);
        when(medecinRepository.findAllBy(any(Pageable.class))).thenReturn(medecinPage);
        when(medecinMapper.toDtoPage(medecinPage)).thenReturn(medecinDtoPage);

        Page<MedecinDTO> result = medecinService.getMedecinsPage(0, 1);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("DOC001", result.getContent().get(0).getMatricule());
        verify(medecinRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    @DisplayName("Devrait refuser un matricule dupliqué")
    void testCreateMedecinDuplicateMatricule() {
        when(medecinRepository.findByMatricule("DOC001")).thenReturn(medecin);

        assertThrows(DuplicateResourceException.class, () -> medecinService.createMedecin(medecinDTO));
        verify(medecinRepository, never()).save(any(Medecin.class));
    }
}
