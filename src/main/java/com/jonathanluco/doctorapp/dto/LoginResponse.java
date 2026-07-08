package com.jonathanluco.doctorapp.dto;

/**
 * Reponse de connexion avec token JWT.
 */
public record LoginResponse(String token, String type) {
}
