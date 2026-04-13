package com.mutualser.employee.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private PasswordEncoderAdapter passwordEncoderAdapter;

    @BeforeEach
    void setUp() {
        passwordEncoderAdapter = new PasswordEncoderAdapter(passwordEncoder);
    }

    @Test
    void encode_ShouldEncodePassword() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        String result = passwordEncoderAdapter.encode("rawPassword");

        assertEquals("encodedPassword", result);
        verify(passwordEncoder).encode("rawPassword");
    }

    @Test
    void matches_WithMatchingPasswords_ShouldReturnTrue() {
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        boolean result = passwordEncoderAdapter.matches("rawPassword", "encodedPassword");

        assertTrue(result);
        verify(passwordEncoder).matches("rawPassword", "encodedPassword");
    }

    @Test
    void matches_WithNonMatchingPasswords_ShouldReturnFalse() {
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        boolean result = passwordEncoderAdapter.matches("wrongPassword", "encodedPassword");

        assertFalse(result);
        verify(passwordEncoder).matches("wrongPassword", "encodedPassword");
    }
}
