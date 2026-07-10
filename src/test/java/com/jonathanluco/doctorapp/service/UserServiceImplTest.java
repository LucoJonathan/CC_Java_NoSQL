package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import com.jonathanluco.doctorapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceImplTest {

    @Mock
    private PatientRepository userRepository;

    @Mock
    private MedecinRepository medecinRepository;

    @Mock
    private UserRepository legacyUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private Patient patient;
    private Medecin medecin;
    private UserRequest request;

    @BeforeEach
    void setUp() {
        patient = new Patient("123456789012", "Jean Dupont", "jean.dupont@example.com", "jean_dupont", "hashed");
        patient.setId("p1");
        medecin = new Medecin("DOC001", "Dr Martin", "dr.martin@example.com", "dr_martin", "hashed");
        medecin.setId("m1");
        request = new UserRequest("new.user@example.com", "Password1!");
    }

    @Test
    void createShouldSavePatient() {
        when(userRepository.findByEmail(request.email())).thenReturn(null);
        when(medecinRepository.findByEmail(request.email())).thenReturn(null);
        when(legacyUserRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("hashed");
        Patient saved = new Patient("123456789012", "Jean Dupont", request.email(), request.email(), "hashed");
        saved.setId("p2");
        when(userRepository.save(any(Patient.class))).thenReturn(saved);

        User created = userService.create(request);

        assertEquals(request.email(), created.getEmail());
        verify(userRepository).save(any(Patient.class));
    }

    @Test
    void createShouldRejectDuplicateEmail() {
        when(userRepository.findByEmail(request.email())).thenReturn(patient);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.create(request));
        assertEquals(409, ex.getStatusCode().value());
        verify(userRepository, never()).save(any());
    }

    @Test
    void authenticateShouldReturnUser() {
        when(userRepository.findByEmail(patient.getEmail())).thenReturn(patient);
        when(passwordEncoder.matches("Password1!", patient.getPassword())).thenReturn(true);

        User found = userService.authenticate(patient.getEmail(), "Password1!");

        assertEquals(patient.getEmail(), found.getEmail());
    }

    @Test
    void authenticateShouldRejectBadCredentials() {
        when(userRepository.findByEmail(patient.getEmail())).thenReturn(patient);
        when(passwordEncoder.matches("bad", patient.getPassword())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> userService.authenticate(patient.getEmail(), "bad"));
    }

    @Test
    void findAllShouldReturnPatients() {
        when(userRepository.findAll()).thenReturn(List.of(patient));

        List<User> users = userService.findAll();

        assertEquals(1, users.size());
    }

    @Test
    void findByEmailShouldSearchAllSources() {
        when(userRepository.findByEmail("a@a.com")).thenReturn(null);
        when(medecinRepository.findByEmail("a@a.com")).thenReturn(medecin);

        assertEquals("dr_martin", userService.findByEmail("a@a.com").getUsername());
    }

    @Test
    void findByIdShouldReturnPatient() {
        when(userRepository.findById("p1")).thenReturn(Optional.of(patient));

        assertEquals("p1", userService.findById("p1").getId());
    }

    @Test
    void updateShouldChangeUser() {
        UserRequest update = new UserRequest("jean.dupont@example.com", "Password2!");
        when(userRepository.findById("p1")).thenReturn(Optional.of(patient));
        when(passwordEncoder.encode(update.password())).thenReturn("hashed2");
        when(userRepository.save(any(Patient.class))).thenReturn(patient);

        User updated = userService.update("p1", update);

        assertEquals(update.email(), updated.getEmail());
        verify(userRepository).save(any(Patient.class));
    }

    @Test
    void deleteShouldCallRepository() {
        userService.delete("p1");
        verify(userRepository).deleteById("p1");
    }
}
