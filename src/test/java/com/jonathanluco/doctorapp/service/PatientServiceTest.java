package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour PatientService.
 * Teste les opérations CRUD des patients.
 */
@DisplayName("PatientService Tests")
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("123456789012", "Jean Dupont", "jean_dupont", "password123");
    }

    @Test
    @DisplayName("Devrait créer un patient avec succès")
    void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient created = patientService.createPatient(patient);

        assertNotNull(created);
        assertEquals("123456789012", created.getNumeroSS());
        assertEquals("Jean Dupont", created.getNomPatient());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    @DisplayName("Devrait récupérer un patient par numeroSS")
    void testGetPatientByNumeroSS() {
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);

        Optional<Patient> found = patientService.getPatientByNumeroSS("123456789012");

        assertTrue(found.isPresent());
        assertEquals("Jean Dupont", found.get().getNomPatient());
        verify(patientRepository, times(1)).findByNumeroSS("123456789012");
    }

    @Test
    @DisplayName("Devrait retourner vide si patient non trouvé")
    void testGetPatientByNumeroSSNotFound() {
        when(patientRepository.findByNumeroSS(anyString())).thenReturn(null);

        Optional<Patient> found = patientService.getPatientByNumeroSS("999999999999");

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Devrait récupérer tous les patients")
    void testGetAllPatients() {
        Patient patient2 = new Patient("987654321098", "Marie Martin", "marie_martin", "password456");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, patient2));

        List<Patient> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(2, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait mettre à jour un patient")
    void testUpdatePatient() {
        Patient updatedData = new Patient("123456789012", "Jean Dupont Modifié", "jean_dupont", "newpassword");
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedData);

        Patient updated = patientService.updatePatient("123456789012", updatedData);

        assertNotNull(updated);
        assertEquals("Jean Dupont Modifié", updated.getNomPatient());
        verify(patientRepository, times(1)).findByNumeroSS("123456789012");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    @DisplayName("Devrait supprimer un patient")
    void testDeletePatient() {
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);
        doNothing().when(patientRepository).delete(patient);

        boolean deleted = patientService.deletePatient("123456789012");

        assertTrue(deleted);
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    @DisplayName("Devrait retourner false si patient non trouvé pour suppression")
    void testDeletePatientNotFound() {
        when(patientRepository.findByNumeroSS(anyString())).thenReturn(null);

        boolean deleted = patientService.deletePatient("999999999999");

        assertFalse(deleted);
    }
}
