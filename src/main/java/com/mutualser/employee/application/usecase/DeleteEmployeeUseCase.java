package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.EmployeeNotFoundException;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

public class DeleteEmployeeUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public DeleteEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public void execute(Long id) {
        if (!employeeRepositoryPort.findById(id).isPresent()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employeeRepositoryPort.deleteById(id);
    }
}
