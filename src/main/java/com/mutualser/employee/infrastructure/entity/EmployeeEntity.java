package com.mutualser.employee.infrastructure.entity;

import com.mutualser.employee.domain.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Employee.Gender gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    public Employee toDomain() {
        return Employee.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .gender(this.gender)
                .age(this.age)
                .email(this.email)
                .build();
    }

    public static EmployeeEntity fromDomain(Employee employee) {
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender())
                .age(employee.getAge())
                .email(employee.getEmail())
                .build();
    }
}
