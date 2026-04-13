package com.mutualser.employee.infrastructure.config;

import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.domain.port.PasswordEncoderPort;
import com.mutualser.employee.domain.port.UserRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public DataInitializer(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepositoryPort.findByUsername("admin").isPresent()) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoderPort.encode("admin123*"))
                    .build();
            userRepositoryPort.save(adminUser);
            log.info("Admin user created successfully with username: admin and password: admin123*");
        }
    }
}
