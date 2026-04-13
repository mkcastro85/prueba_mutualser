package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

import java.util.List;

public class GetEmployeesByGenderUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public GetEmployeesByGenderUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public List<Employee> execute(Employee.Gender gender) {
        return employeeRepositoryPort.findByGender(gender);
    }
}
