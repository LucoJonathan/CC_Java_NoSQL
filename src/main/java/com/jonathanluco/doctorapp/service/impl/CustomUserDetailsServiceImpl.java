package com.jonathanluco.doctorapp.service.impl;

import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import com.jonathanluco.doctorapp.service.ICustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation UserDetailsService custom.
 */
@Service
public class CustomUserDetailsServiceImpl implements ICustomUserDetailsService {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            UserRepository userRepository
    ) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.userRepository = userRepository;
    }

    /**
     * Charge utilisateur Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User introuvable");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
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
