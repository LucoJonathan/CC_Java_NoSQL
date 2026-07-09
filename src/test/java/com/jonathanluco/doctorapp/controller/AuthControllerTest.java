package com.jonathanluco.doctorapp.controller;

import com.jonathanluco.doctorapp.dto.LoginRequest;
import com.jonathanluco.doctorapp.model.Patient;
import com.jonathanluco.doctorapp.model.User;
import com.jonathanluco.doctorapp.service.IJwtService;
import com.jonathanluco.doctorapp.service.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Mock
    private IUserService userService;

    @Mock
    private IJwtService jwtService;

    @InjectMocks
    private AuthController controller;

    @Test
    void loginShouldReturnToken() {
        Patient user = new Patient("1", "Jean", "j@example.com", "jean", "pwd");
        when(userService.authenticate("j@example.com", "Password1!")).thenReturn(user);
        when(jwtService.generateToken(org.mockito.ArgumentMatchers.any(UserDetails.class))).thenReturn("token");

        var response = controller.login(new LoginRequest("j@example.com", "Password1!"));

        assertEquals("token", response.token());
        assertEquals("Bearer", response.type());
    }
}
