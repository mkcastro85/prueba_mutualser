package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.InvalidEmployeeDataException;
import com.mutualser.employee.domain.exception.MaxEmployeesReachedException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

public class CreateEmployeeUseCase {
    private static final int MAX_EMPLOYEES = 30;
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public CreateEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public Employee execute(Employee employee) {
        validateEmployee(employee);

        long currentCount = employeeRepositoryPort.count();
        if (currentCount >= MAX_EMPLOYEES) {
            throw new MaxEmployeesReachedException("Maximum number of employees (30) has been reached");
        }

        return employeeRepositoryPort.save(employee);
    }

    private void validateEmployee(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new InvalidEmployeeDataException("First name is required");
        }
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new InvalidEmployeeDataException("Last name is required");
        }
        if (employee.getGender() == null) {
            throw new InvalidEmployeeDataException("Gender is required");
        }
        if (employee.getAge() == null || employee.getAge() <= 0) {
            throw new InvalidEmployeeDataException("Valid age is required");
        }
        if (employee.getEmail() == null || !employee.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidEmployeeDataException("Valid email is required");
        }
    }
}
