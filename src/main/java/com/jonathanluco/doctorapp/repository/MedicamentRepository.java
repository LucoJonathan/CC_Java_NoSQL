package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.Medicament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends MongoRepository<Medicament, String> {
    Medicament findByCode(String code);
}
