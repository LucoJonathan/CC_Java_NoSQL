package com.jonathanluco.doctorapp.service.impl;

import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.model.Medecin;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.repository.MedecinRepository;
import com.jonathanluco.doctorapp.repository.PatientRepository;
import com.jonathanluco.doctorapp.repository.UserRepository;
import com.jonathanluco.doctorapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private PatientRepository userRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private UserRepository legacyUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(UserRequest request) {
        if (findByEmail(request.email()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email existe deja");
        }

        Patient patient = new Patient();
        patient.setEmail(request.email());
        patient.setUsername(request.email());
        patient.setPassword(passwordEncoder.encode(request.password()));
        patient.setNumeroSS("UNKNOWN");
        patient.setNomPatient("User - " + request.email());
        return userRepository.save(patient);
    }

    @Override
    public User authenticate(String email, String password) {
        User user = findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credentials invalides");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(p -> (User) p)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
    public User findById(String id) {
        Optional<Patient> patient = userRepository.findById(id);
        return patient.orElse(null);
    }

    @Override
    public User update(String id, UserRequest request) {
        Optional<Patient> patient = userRepository.findById(id);
        if (patient.isPresent()) {
            Patient p = patient.get();
            User existing = findByEmail(request.email());
            if (existing != null && !existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email existe deja");
            }
            p.setEmail(request.email());
            p.setUsername(request.email());
            p.setPassword(passwordEncoder.encode(request.password()));
            return userRepository.save(p);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouve");
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
