package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.service.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController controller;

    @Test
    void createShouldReturnResponse() {
        Patient user = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        user.setId("u1");
        when(userService.create(any(UserRequest.class))).thenReturn(user);

        var response = controller.create(new UserRequest("j@example.com", "Password1!"));

        assertEquals("u1", response.id());
        assertEquals("j@example.com", response.email());
    }

    @Test
    void findAllShouldMapUsers() {
        Patient user = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        user.setId("u1");
        when(userService.findAll()).thenReturn(List.of(user));

        assertEquals(1, controller.findAll().size());
    }

    @Test
    void findByIdShouldReturnResponse() {
        Patient user = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        user.setId("u1");
        when(userService.findById("u1")).thenReturn(user);

        assertEquals("u1", controller.findById("u1").id());
    }

    @Test
    void updateShouldReturnResponse() {
        Patient user = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        user.setId("u1");
        when(userService.update(eq("u1"), any(UserRequest.class))).thenReturn(user);

        assertEquals("j@example.com", controller.update("u1", new UserRequest("j@example.com", "Password1!")).email());
    }

    @Test
    void deleteShouldCallService() {
        controller.delete("u1");
        verify(userService).delete("u1");
    }
}
