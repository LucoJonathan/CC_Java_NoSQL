package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository MongoDB pour les consultations.
 */
@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, String> {
    Optional<Consultation> findByNumeroConsultation(String numeroConsultation);
    boolean existsByNumeroConsultation(String numeroConsultation);
    void deleteByNumeroConsultation(String numeroConsultation);
    List<Consultation> findByPatientAssiste_NumeroSS(String numeroSS);
    List<Consultation> findByMedecinDonne_Matricule(String matricule);
}
