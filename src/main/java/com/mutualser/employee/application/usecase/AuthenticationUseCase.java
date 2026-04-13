package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.InvalidCredentialsException;
import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.domain.port.JwtServicePort;
import com.mutualser.employee.domain.port.PasswordEncoderPort;
import com.mutualser.employee.domain.port.UserRepositoryPort;

public class AuthenticationUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtServicePort jwtServicePort;

    public AuthenticationUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtServicePort jwtServicePort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtServicePort = jwtServicePort;
    }

    public String authenticate(String username, String password) {
        User user = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return jwtServicePort.generateToken(username);
    }
}
