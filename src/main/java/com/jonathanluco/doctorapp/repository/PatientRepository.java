package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findByNumeroSS(String numeroSS);
    Patient findByUsername(String username);
}
