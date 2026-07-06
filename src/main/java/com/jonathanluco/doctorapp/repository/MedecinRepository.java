package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Medecin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends MongoRepository<Medecin, String> {
    Medecin findByMatricule(String matricule);
    Medecin findByUsername(String username);
}
