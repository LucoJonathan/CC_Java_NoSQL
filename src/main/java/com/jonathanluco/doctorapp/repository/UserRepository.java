package com.jonathanluco.doctorapp.repository;

import com.jonathanluco.doctorapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository legacy pour les utilisateurs generiques.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
