package com.mutualser.employee.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceAdapterTest {

    private JwtServiceAdapter jwtServiceAdapter;

    @BeforeEach
    void setUp() {
        jwtServiceAdapter = new JwtServiceAdapter();
        ReflectionTestUtils.setField(jwtServiceAdapter, "secretKey", 
            "mySecretKeyForJWTTokenGenerationThatIsLongEnough1234567890");
        ReflectionTestUtils.setField(jwtServiceAdapter, "expiration", 86400000L);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        String token = jwtServiceAdapter.generateToken("admin");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ShouldExtractUsernameFromToken() {
        String token = jwtServiceAdapter.generateToken("admin");

        String username = jwtServiceAdapter.extractUsername(token);

        assertEquals("admin", username);
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        String token = jwtServiceAdapter.generateToken("admin");

        boolean isValid = jwtServiceAdapter.validateToken(token, "admin");

        assertTrue(isValid);
    }

    @Test
    void validateToken_WithInvalidUsername_ShouldReturnFalse() {
        String token = jwtServiceAdapter.generateToken("admin");

        boolean isValid = jwtServiceAdapter.validateToken(token, "wrongUser");

        assertFalse(isValid);
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() throws InterruptedException {
        JwtServiceAdapter shortExpirationAdapter = new JwtServiceAdapter();
        ReflectionTestUtils.setField(shortExpirationAdapter, "secretKey", 
            "mySecretKeyForJWTTokenGenerationThatIsLongEnough1234567890");
        ReflectionTestUtils.setField(shortExpirationAdapter, "expiration", 1L);

        String token = shortExpirationAdapter.generateToken("admin");
        
        Thread.sleep(100);

        boolean isValid = shortExpirationAdapter.validateToken(token, "admin");

        assertFalse(isValid);
    }
}
