package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Patient.
 * Fournit les opérations CRUD et des requêtes personnalisées sur la collection "patients".
 *
 */
@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    /**
     * Trouve un patient par son numéro de sécurité sociale.
     *
     * @param numeroSS le numéro de sécurité sociale
     * @return le Patient ou null s'il n'existe pas
     */
    Patient findByNumeroSS(String numeroSS);

    Patient findByEmail(String email);

    Page<Patient> findAllBy(Pageable pageable);
}
