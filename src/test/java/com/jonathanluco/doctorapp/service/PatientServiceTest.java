package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.PatientDTO;
import com.jonathanluco.doctorapp.exception.DuplicateResourceException;
import com.jonathanluco.doctorapp.mapper.PatientMapper;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private MedecinRepository medecinRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("123456789012", "Jean Dupont", "jean.dupont@example.com", "jean_dupont", "password123");
        patient.setId("patient-1");
        patientDTO = new PatientDTO("123456789012", "Jean Dupont", "jean_dupont", "jean.dupont@example.com", "password123");
    }

    @Test
    @DisplayName("Devrait créer un patient avec succès")
    void testCreatePatient() {
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(null);
        when(patientRepository.findByEmail("jean.dupont@example.com")).thenReturn(null);
        when(medecinRepository.findByEmail("jean.dupont@example.com")).thenReturn(null);
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
    @DisplayName("Devrait refuser un numeroSS dupliqué")
    void testCreatePatientDuplicateNumeroSS() {
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);

        assertThrows(DuplicateResourceException.class, () -> patientService.createPatient(patientDTO));
        verify(patientRepository, never()).save(any(Patient.class));
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
        Patient patient2 = new Patient("987654321098", "Marie Martin", "marie.martin@example.com", "marie_martin", "password456");
        PatientDTO patientDTO2 = new PatientDTO("987654321098", "Marie Martin", "marie_martin", "marie.martin@example.com", "password456");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, patient2));
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);
        when(patientMapper.toDto(patient2)).thenReturn(patientDTO2);

        List<PatientDTO> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(2, patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait récupérer une page de patients")
    void testGetPatientsPage() {
        Page<Patient> patientPage = new PageImpl<>(List.of(patient), PageRequest.of(0, 1), 1);
        Page<PatientDTO> patientDtoPage = new PageImpl<>(List.of(patientDTO), PageRequest.of(0, 1), 1);
        when(patientRepository.findAllBy(any(Pageable.class))).thenReturn(patientPage);
        when(patientMapper.toDtoPage(patientPage)).thenReturn(patientDtoPage);

        Page<PatientDTO> result = patientService.getPatientsPage(0, 1);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("123456789012", result.getContent().get(0).getNumeroSS());
        verify(patientRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    @DisplayName("Devrait mettre à jour un patient")
    void testUpdatePatient() {
        PatientDTO updatedData = new PatientDTO("123456789012", "Jean Dupont Modifié", "jean_dupont", "jean.dupont@example.com", "newpassword");
        Patient updatedPatient = new Patient("123456789012", "Jean Dupont Modifié", "jean.dupont@example.com", "jean_dupont", "newpassword");
        when(patientRepository.findByNumeroSS("123456789012")).thenReturn(patient);
        when(patientRepository.findByEmail("jean.dupont@example.com")).thenReturn(patient);
        when(medecinRepository.findByEmail("jean.dupont@example.com")).thenReturn(null);
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
