package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service pour les opérations utilisateur.
 * Gère la création et la récupération des utilisateurs (Patients et Médecins).
 *
 */
@Service
public class UserService {

    @Autowired
    private PatientRepository userRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private UserRepository legacyUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crée un nouvel utilisateur (Patient).
     * Valide que le username n'existe pas déjà.
     *
     * @param request les données de création d'utilisateur
     * @return l'utilisateur créé
     * @throws ResponseStatusException si le username existe déjà
     */
    public User create(UserRequest request) {
        if (findByEmail(request.email()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email existe déjà");
        }

        Patient patient = new Patient();
        patient.setEmail(request.email());
        patient.setUsername(request.email());
        patient.setPassword(passwordEncoder.encode(request.password()));
        patient.setNumeroSS("UNKNOWN");
        patient.setNomPatient("User - " + request.email());
        return userRepository.save(patient);
    }

    /**
     * Authentifie un utilisateur avec username et password.
     *
     * @param username le nom d'utilisateur
     * @param password le mot de passe
     * @return l'utilisateur authentifié
     * @throws ResponseStatusException si authentification échoue
     */
    public User authenticate(String email, String password) {
        User user = findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credentials invalides");
        }
        return user;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return liste de tous les utilisateurs
     */
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(p -> (User) p)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email l'email
     * @return l'utilisateur ou null s'il n'existe pas
     */
    public User findByEmail(String email) {
        Patient patient = userRepository.findByEmail(email);
        if (patient != null) {
            return patient;
        }

        Medecin medecin = medecinRepository.findByEmail(email);
        if (medecin != null) {
            return medecin;
        }

        return legacyUserRepository.findByEmail(email).orElse(null);
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id l'identifiant MongoDB
     * @return l'utilisateur
     */
    public User findById(String id) {
        Optional<Patient> patient = userRepository.findById(id);
        return patient.orElse(null);
    }

    /**
     * Met à jour un utilisateur.
     *
     * @param id l'identifiant MongoDB
     * @param request les nouvelles données
     * @return l'utilisateur mis à jour
     */
    public User update(String id, UserRequest request) {
        Optional<Patient> patient = userRepository.findById(id);
        if (patient.isPresent()) {
            Patient p = patient.get();
            User existing = findByEmail(request.email());
            if (existing != null && !existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email existe déjà");
            }
            p.setEmail(request.email());
            p.setUsername(request.email());
            p.setPassword(passwordEncoder.encode(request.password()));
            return userRepository.save(p);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
    }

    /**
     * Supprime un utilisateur.
     *
     * @param id l'identifiant MongoDB
     */
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
