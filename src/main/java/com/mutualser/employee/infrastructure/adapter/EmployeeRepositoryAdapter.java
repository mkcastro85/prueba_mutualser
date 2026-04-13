package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import com.mutualser.employee.infrastructure.repository.EmployeeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {
    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeRepositoryAdapter(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = EmployeeEntity.fromDomain(employee);
        EmployeeEntity savedEntity = employeeJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeJpaRepository.findById(id)
                .map(EmployeeEntity::toDomain);
    }

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll().stream()
                .map(EmployeeEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        employeeJpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return employeeJpaRepository.count();
    }

    @Override
    public List<Employee> findByAgeGreaterThanEqual(Integer age) {
        return employeeJpaRepository.findByAgeGreaterThanEqual(age).stream()
                .map(EmployeeEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findByGender(Employee.Gender gender) {
        return employeeJpaRepository.findByGender(gender).stream()
                .map(EmployeeEntity::toDomain)
                .collect(Collectors.toList());
    }
}
