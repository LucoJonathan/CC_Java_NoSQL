package com.jonathanluco.doctorapp.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DTO Validation Tests")
class DtoValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void userRequestShouldValidate() {
        var violations = validator.validate(new UserRequest("", "short"));
        assertFalse(violations.isEmpty());
    }

    @Test
    void loginRequestShouldValidate() {
        var violations = validator.validate(new LoginRequest("", ""));
        assertFalse(violations.isEmpty());
    }

    @Test
    void medecinDtoShouldValidate() {
        var violations = validator.validate(new MedecinDTO("", "", "", "bad", "short"));
        assertFalse(violations.isEmpty());
    }

    @Test
    void patientDtoShouldValidate() {
        var violations = validator.validate(new PatientDTO("", "", "", "bad", "short"));
        assertFalse(violations.isEmpty());
    }

    @Test
    void consultationDtoShouldValidate() {
        var violations = validator.validate(new ConsultationDTO("", null));
        assertFalse(violations.isEmpty());
    }

    @Test
    void medicamentDtoShouldValidate() {
        var violations = validator.validate(new MedicamentDTO("", ""));
        assertFalse(violations.isEmpty());
    }

    @Test
    void prescriptionDtoShouldValidate() {
        var violations = validator.validate(new PrescriptionDTO("", 0));
        assertFalse(violations.isEmpty());
    }
}
