package com.mutualser.employee.infrastructure.repository;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByAgeGreaterThanEqual(Integer age);
    List<EmployeeEntity> findByGender(Employee.Gender gender);
}
