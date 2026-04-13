package com.mutualser.employee.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String email;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
