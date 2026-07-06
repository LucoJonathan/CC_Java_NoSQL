package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation, String> {
    List<Consultation> findByPatientAssiste_NumeroSS(String numeroSS);
    List<Consultation> findByMedecinDonne_Matricule(String matricule);
}
