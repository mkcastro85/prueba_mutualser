package com.mutualser.employee.domain.port;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.model.Employee.Gender;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    void deleteById(Long id);
    long count();
    List<Employee> findByAgeGreaterThanEqual(Integer age);
    List<Employee> findByGender(Gender gender);
}
