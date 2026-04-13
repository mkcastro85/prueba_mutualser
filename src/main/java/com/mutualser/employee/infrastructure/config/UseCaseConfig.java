package com.mutualser.employee.infrastructure.config;

import com.mutualser.employee.application.usecase.*;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import com.mutualser.employee.domain.port.JwtServicePort;
import com.mutualser.employee.domain.port.PasswordEncoderPort;
import com.mutualser.employee.domain.port.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AuthenticationUseCase authenticationUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtServicePort jwtServicePort) {
        return new AuthenticationUseCase(userRepositoryPort, passwordEncoderPort, jwtServicePort);
    }

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new CreateEmployeeUseCase(employeeRepositoryPort);
    }

    @Bean
    public GetEmployeeUseCase getEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new GetEmployeeUseCase(employeeRepositoryPort);
    }

    @Bean
    public UpdateEmployeeUseCase updateEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new UpdateEmployeeUseCase(employeeRepositoryPort);
    }

    @Bean
    public DeleteEmployeeUseCase deleteEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new DeleteEmployeeUseCase(employeeRepositoryPort);
    }

    @Bean
    public GetAllEmployeesUseCase getAllEmployeesUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new GetAllEmployeesUseCase(employeeRepositoryPort);
    }

    @Bean
    public GetEmployeesByAgeUseCase getEmployeesByAgeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new GetEmployeesByAgeUseCase(employeeRepositoryPort);
    }

    @Bean
    public GetEmployeesByGenderUseCase getEmployeesByGenderUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new GetEmployeesByGenderUseCase(employeeRepositoryPort);
    }
}
