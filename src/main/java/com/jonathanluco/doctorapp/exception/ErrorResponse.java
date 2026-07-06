package com.jonathanluco.doctorapp.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * Structure standard pour les réponses d'erreur.
 */
@Schema(description = "Objet d'erreur standardisé")
public class ErrorResponse {

    @Schema(description = "Code de statut HTTP", example = "404")
    private int status;

    @Schema(description = "Message d'erreur", example = "Patient non trouvé")
    private String message;

    @Schema(description = "Timestamp de l'erreur")
    private LocalDateTime timestamp;

    @Schema(description = "Chemin de la requête", example = "/api/patients/123")
    private String path;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String message, String path) {
        this();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
