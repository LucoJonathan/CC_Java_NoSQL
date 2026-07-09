package com.jonathanluco.doctorapp.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest("GET", "/api/test"));

    @Test
    void handleNotFoundShouldReturn404() {
        var response = handler.handleResourceNotFoundException(new ResourceNotFoundException("Patient", "id", "1"), request);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void handleDuplicateShouldReturn409() {
        var response = handler.handleDuplicateResourceException(new DuplicateResourceException("dup"), request);
        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    void handleDuplicateKeyShouldReturn409() {
        var response = handler.handleDuplicateKeyException(new DuplicateKeyException("dup"), request);
        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    void handleValidationShouldReturn400() throws Exception {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(org.springframework.validation.BindingResult.class);
        var fieldError = mock(org.springframework.validation.FieldError.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getDefaultMessage()).thenReturn("bad");

        var response = handler.handleMethodArgumentNotValidException(ex, request);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void handleConstraintViolationShouldReturn400() {
        var response = handler.handleConstraintViolationException(new ConstraintViolationException("bad", java.util.Set.of()), request);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void handleGlobalShouldReturn500() {
        var response = handler.handleGlobalException(new RuntimeException("boom"), request);
        assertEquals(500, response.getStatusCode().value());
    }
}
