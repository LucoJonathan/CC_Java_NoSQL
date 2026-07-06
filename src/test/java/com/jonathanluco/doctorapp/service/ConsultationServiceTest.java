package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Consultation;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Prescription;
import com.jonathanluco.doctorapp.repository.ConsultationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ConsultationService.
 * Teste les opérations CRUD des consultations et la gestion des prescriptions.
 */
@DisplayName("ConsultationService Tests")
class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private ConsultationService consultationService;

    private Consultation consultation;
    private Patient patient;
    private Medecin medecin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("123456789012", "Jean Dupont", "jean_dupont", "password123");
        medecin = new Medecin("DOC001", "Dr. Martin", "dr_martin", "password123");
        consultation = new Consultation("CONS001", LocalDateTime.now());
        consultation.setPatientAssiste(patient);
        consultation.setMedecinDonne(medecin);
    }

    @Test
    @DisplayName("Devrait créer une consultation avec succès")
    void testCreateConsultation() {
        when(consultationRepository.save(any(Consultation.class))).thenReturn(consultation);

        Consultation created = consultationService.createConsultation(consultation);

        assertNotNull(created);
        assertEquals("CONS001", created.getNumeroConsultation());
        verify(consultationRepository, times(1)).save(consultation);
    }

    @Test
    @DisplayName("Devrait récupérer une consultation par ID")
    void testGetConsultationById() {
        when(consultationRepository.findById("CONS001")).thenReturn(Optional.of(consultation));

        Optional<Consultation> found = consultationService.getConsultationById("CONS001");

        assertTrue(found.isPresent());
        assertEquals("CONS001", found.get().getNumeroConsultation());
        verify(consultationRepository, times(1)).findById("CONS001");
    }

    @Test
    @DisplayName("Devrait récupérer toutes les consultations")
    void testGetAllConsultations() {
        when(consultationRepository.findAll()).thenReturn(Arrays.asList(consultation));

        List<Consultation> consultations = consultationService.getAllConsultations();

        assertNotNull(consultations);
        assertEquals(1, consultations.size());
        verify(consultationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait récupérer les consultations d'un patient")
    void testGetConsultationsByPatient() {
        when(consultationRepository.findByPatientAssiste_NumeroSS("123456789012"))
                .thenReturn(Arrays.asList(consultation));

        List<Consultation> consultations = consultationService.getConsultationsByPatient("123456789012");

        assertNotNull(consultations);
        assertEquals(1, consultations.size());
        verify(consultationRepository, times(1)).findByPatientAssiste_NumeroSS("123456789012");
    }

    @Test
    @DisplayName("Devrait ajouter une prescription à une consultation")
    void testAddPrescriptionToConsultation() {
        Prescription prescription = new Prescription("MED001", 3);
        when(consultationRepository.findById("CONS001")).thenReturn(Optional.of(consultation));
        when(consultationRepository.save(any(Consultation.class))).thenReturn(consultation);

        Consultation updated = consultationService.addPrescriptionToConsultation("CONS001", prescription);

        assertNotNull(updated);
        assertEquals(1, updated.getPrescriptions().size());
        verify(consultationRepository, times(1)).findById("CONS001");
    }

    @Test
    @DisplayName("Devrait supprimer une consultation")
    void testDeleteConsultation() {
        when(consultationRepository.existsById("CONS001")).thenReturn(true);
        doNothing().when(consultationRepository).deleteById("CONS001");

        boolean deleted = consultationService.deleteConsultation("CONS001");

        assertTrue(deleted);
        verify(consultationRepository, times(1)).deleteById("CONS001");
    }
}
