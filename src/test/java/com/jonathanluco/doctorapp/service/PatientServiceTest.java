package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.mapper.PatientMapper;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("123456789012", "Jean Dupont", "jean_dupont", "password123");
        patientDTO = new PatientDTO("123456789012", "Jean Dupont", "jean_dupont", "password123");
    }

    @Test
    @DisplayName("Devrait créer un patient avec succès")
    void testCreatePatient() {
        when(patientMapper.toModel(any(PatientDTO.class))).thenReturn(patient);
        when(passwordEncoder.encode("password123")).thenReturn("hashed-password123");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientMapper.toDto(any(Patient.class))).thenReturn(patientDTO);

        PatientDTO created = patientService.createPatient(patientDTO);

        assertNotNull(created);
        assertEquals("123456789012", created.getNumeroSS());
        assertEquals("Jean Dupont", created.getNomPatient());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    @DisplayName("Devrait récupérer un patient par numeroSS")
    void testGetPatientByNumeroSS() {
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);

        Optional<PatientDTO> found = patientService.getPatientByNumeroSS("123456789012");

        assertTrue(found.isPresent());
        assertEquals("Jean Dupont", found.get().getNomPatient());
        verify(patientRepository, times(1)).findByNumeroSS("123456789012");
    }

    @Test
    @DisplayName("Devrait retourner vide si patient non trouvé")
    void testGetPatientByNumeroSSNotFound() {
        when(patientRepository.findByNumeroSS(anyString())).thenReturn(null);

        Optional<PatientDTO> found = patientService.getPatientByNumeroSS("999999999999");

        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Devrait récupérer tous les patients")
    void testGetAllPatients() {
        Patient patient2 = new Patient("987654321098", "Marie Martin", "marie_martin", "password456");
        PatientDTO patientDTO2 = new PatientDTO("987654321098", "Marie Martin", "marie_martin", "password456");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, patient2));
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);
        when(patientMapper.toDto(patient2)).thenReturn(patientDTO2);

        List<PatientDTO> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(2, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait mettre à jour un patient")
    void testUpdatePatient() {
        PatientDTO updatedData = new PatientDTO("123456789012", "Jean Dupont Modifié", "jean_dupont", "newpassword");
        Patient updatedPatient = new Patient("123456789012", "Jean Dupont Modifié", "jean_dupont", "newpassword");
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);
        when(passwordEncoder.encode("newpassword")).thenReturn("hashed-newpassword");
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);
        doNothing().when(patientMapper).updateModel(patient, updatedData);
        when(patientMapper.toDto(updatedPatient)).thenReturn(updatedData);

        PatientDTO updated = patientService.updatePatient("123456789012", updatedData);

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
