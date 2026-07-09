package com.jonathanluco.doctorapp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PingController Tests")
class PingControllerTest {

    @Test
    void pingShouldReturnPong() {
        assertEquals("pong", new PingController().ping().get("message"));
    }
}
