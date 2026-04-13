package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.InvalidCredentialsException;
import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.domain.port.JwtServicePort;
import com.mutualser.employee.domain.port.PasswordEncoderPort;
import com.mutualser.employee.domain.port.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Mock
    private JwtServicePort jwtServicePort;

    private AuthenticationUseCase authenticationUseCase;

    @BeforeEach
    void setUp() {
        authenticationUseCase = new AuthenticationUseCase(userRepositoryPort, passwordEncoderPort, jwtServicePort);
    }

    @Test
    void authenticate_WithValidCredentials_ShouldReturnToken() {
        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        when(userRepositoryPort.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("admin123*", "encodedPassword")).thenReturn(true);
        when(jwtServicePort.generateToken("admin")).thenReturn("jwt-token");

        String token = authenticationUseCase.authenticate("admin", "admin123*");

        assertEquals("jwt-token", token);
        verify(userRepositoryPort).findByUsername("admin");
        verify(passwordEncoderPort).matches("admin123*", "encodedPassword");
        verify(jwtServicePort).generateToken("admin");
    }

    @Test
    void authenticate_WithInvalidUsername_ShouldThrowException() {
        when(userRepositoryPort.findByUsername("invalid")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> 
            authenticationUseCase.authenticate("invalid", "password"));

        verify(userRepositoryPort).findByUsername("invalid");
        verify(passwordEncoderPort, never()).matches(anyString(), anyString());
        verify(jwtServicePort, never()).generateToken(anyString());
    }

    @Test
    void authenticate_WithInvalidPassword_ShouldThrowException() {
        User user = User.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        when(userRepositoryPort.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> 
            authenticationUseCase.authenticate("admin", "wrongPassword"));

        verify(userRepositoryPort).findByUsername("admin");
        verify(passwordEncoderPort).matches("wrongPassword", "encodedPassword");
        verify(jwtServicePort, never()).generateToken(anyString());
    }
}
