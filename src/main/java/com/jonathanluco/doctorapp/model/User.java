package com.jonathanluco.doctorapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Classe parent représentant un utilisateur du système.
 *
 * Cette classe contient les champs communs à tous les utilisateurs
 * (Patient et Medecin). Elle n'est pas marquée @Document car
 * le mapping hérité se fait au niveau des sous-classes.
 *
 */
public abstract class User {

    /**
     * Identifiant MongoDB automatiquement généré.
     */
    @Id
    protected String id;

    @NotBlank
    @Email
    @Indexed(unique = true)
    protected String email;

    /**
     * Nom d'utilisateur.
     */
    @NotBlank
    protected String username;

    /**
     * Mot de passe chiffré.
     */
    @NotBlank
    protected String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
