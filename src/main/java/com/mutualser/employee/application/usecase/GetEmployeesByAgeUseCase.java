package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

import java.util.List;

public class GetEmployeesByAgeUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public GetEmployeesByAgeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public List<Employee> execute(Integer minAge) {
        return employeeRepositoryPort.findByAgeGreaterThanEqual(minAge);
    }
}
