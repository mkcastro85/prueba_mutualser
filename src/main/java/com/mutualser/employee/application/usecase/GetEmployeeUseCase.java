package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.EmployeeNotFoundException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

public class GetEmployeeUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public GetEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public Employee execute(Long id) {
        return employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }
}
