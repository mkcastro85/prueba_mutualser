package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

import java.util.List;

public class GetAllEmployeesUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public GetAllEmployeesUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public List<Employee> execute() {
        return employeeRepositoryPort.findAll();
    }
}
