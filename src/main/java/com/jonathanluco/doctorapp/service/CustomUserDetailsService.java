package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsService(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            UserRepository userRepository
    ) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User introuvable");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    private User findUserByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email);
        if (patient != null) {
            return patient;
        }

        Medecin medecin = medecinRepository.findByEmail(email);
        if (medecin != null) {
            return medecin;
        }

        return userRepository.findByEmail(email).orElse(null);
    }
}
