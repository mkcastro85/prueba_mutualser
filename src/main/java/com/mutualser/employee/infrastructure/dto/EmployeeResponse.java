package com.mutualser.employee.infrastructure.dto;

import com.mutualser.employee.domain.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Employee.Gender gender;
    private Integer age;
    private String email;
}
