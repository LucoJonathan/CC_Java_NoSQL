package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import com.jonathanluco.doctorapp.service.impl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService Tests")
class CustomUserDetailsServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedecinRepository medecinRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsServiceImpl service;

    @Test
    void loadUserShouldReturnPatient() {
        Patient patient = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        when(patientRepository.findByEmail("j@example.com")).thenReturn(patient);

        UserDetails userDetails = service.loadUserByUsername("j@example.com");

        assertEquals("j@example.com", userDetails.getUsername());
    }

    @Test
    void loadUserShouldReturnMedecin() {
        Medecin medecin = new Medecin("DOC1", "Dr", "d@example.com", "dr", "pwd");
        when(patientRepository.findByEmail("d@example.com")).thenReturn(null);
        when(medecinRepository.findByEmail("d@example.com")).thenReturn(medecin);

        assertEquals("d@example.com", service.loadUserByUsername("d@example.com").getUsername());
    }

    @Test
    void loadUserShouldThrowIfMissing() {
        when(patientRepository.findByEmail("x@example.com")).thenReturn(null);
        when(medecinRepository.findByEmail("x@example.com")).thenReturn(null);
        when(userRepository.findByEmail("x@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("x@example.com"));
    }
}
