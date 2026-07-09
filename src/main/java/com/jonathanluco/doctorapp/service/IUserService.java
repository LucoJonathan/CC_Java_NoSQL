package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.model.User;

import java.util.List;

/**
 * Contrat service utilisateur.
 */
public interface IUserService {
    /**
     * Cree utilisateur.
     *
     * @param request donnees creation
     * @return utilisateur cree
     */
    User create(UserRequest request);

    /**
     * Authentifie utilisateur.
     *
     * @param email email
     * @param password mot de passe
     * @return utilisateur authentifie
     */
    User authenticate(String email, String password);

    /**
     * Liste utilisateurs.
     *
     * @return tous utilisateurs
     */
    List<User> findAll();

    /**
     * Cherche utilisateur par email.
     *
     * @param email email
     * @return utilisateur ou null
     */
    User findByEmail(String email);

    /**
     * Cherche utilisateur par id.
     *
     * @param id identifiant
     * @return utilisateur ou null
     */
    User findById(String id);

    /**
     * Met a jour utilisateur.
     *
     * @param id identifiant
     * @param request nouvelles donnees
     * @return utilisateur mis a jour
     */
    User update(String id, UserRequest request);

    /**
     * Supprime utilisateur.
     *
     * @param id identifiant
     */
    void delete(String id);
}
