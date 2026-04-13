package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.EmployeeNotFoundException;
import com.mutualser.employee.domain.exception.InvalidEmployeeDataException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;

public class UpdateEmployeeUseCase {
    private final EmployeeRepositoryPort employeeRepositoryPort;

    public UpdateEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    public Employee execute(Long id, Employee employee) {
        Employee existingEmployee = employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        validateEmployee(employee);

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setAge(employee.getAge());
        existingEmployee.setEmail(employee.getEmail());

        return employeeRepositoryPort.save(existingEmployee);
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
