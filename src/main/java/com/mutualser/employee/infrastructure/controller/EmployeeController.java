package com.mutualser.employee.infrastructure.controller;

import com.mutualser.employee.application.usecase.*;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.dto.EmployeeRequest;
import com.mutualser.employee.infrastructure.dto.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetEmployeeUseCase getEmployeeUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final DeleteEmployeeUseCase deleteEmployeeUseCase;
    private final GetAllEmployeesUseCase getAllEmployeesUseCase;
    private final GetEmployeesByAgeUseCase getEmployeesByAgeUseCase;
    private final GetEmployeesByGenderUseCase getEmployeesByGenderUseCase;

    public EmployeeController(
            CreateEmployeeUseCase createEmployeeUseCase,
            GetEmployeeUseCase getEmployeeUseCase,
            UpdateEmployeeUseCase updateEmployeeUseCase,
            DeleteEmployeeUseCase deleteEmployeeUseCase,
            GetAllEmployeesUseCase getAllEmployeesUseCase,
            GetEmployeesByAgeUseCase getEmployeesByAgeUseCase,
            GetEmployeesByGenderUseCase getEmployeesByGenderUseCase) {
        this.createEmployeeUseCase = createEmployeeUseCase;
        this.getEmployeeUseCase = getEmployeeUseCase;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
        this.deleteEmployeeUseCase = deleteEmployeeUseCase;
        this.getAllEmployeesUseCase = getAllEmployeesUseCase;
        this.getEmployeesByAgeUseCase = getEmployeesByAgeUseCase;
        this.getEmployeesByGenderUseCase = getEmployeesByGenderUseCase;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee employee = createEmployeeUseCase.execute(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeResponse.fromDomain(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        Employee employee = getEmployeeUseCase.execute(id);
        return ResponseEntity.ok(EmployeeResponse.fromDomain(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        Employee employee = updateEmployeeUseCase.execute(id, request.toDomain());
        return ResponseEntity.ok(EmployeeResponse.fromDomain(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        deleteEmployeeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<Employee> employees = getAllEmployeesUseCase.execute();
        List<EmployeeResponse> response = employees.stream()
                .map(EmployeeResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/age-filter")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByAge(
            @RequestParam(defaultValue = "40") Integer minAge) {
        List<Employee> employees = getEmployeesByAgeUseCase.execute(minAge);
        List<EmployeeResponse> response = employees.stream()
                .map(EmployeeResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByGender(@PathVariable Employee.Gender gender) {
        List<Employee> employees = getEmployeesByGenderUseCase.execute(gender);
        List<EmployeeResponse> response = employees.stream()
                .map(EmployeeResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
