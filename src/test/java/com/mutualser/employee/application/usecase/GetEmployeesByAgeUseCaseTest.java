package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeesByAgeUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private GetEmployeesByAgeUseCase getEmployeesByAgeUseCase;

    @BeforeEach
    void setUp() {
        getEmployeesByAgeUseCase = new GetEmployeesByAgeUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_ShouldReturnEmployeesWithAgeGreaterThanOrEqual() {
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(45)
                .email("meyling.castro@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Andrea")
                .lastName("López")
                .gender(Employee.Gender.FEMALE)
                .age(50)
                .email("andrea.lopez@gmail.com")
                .build();

        when(employeeRepositoryPort.findByAgeGreaterThanEqual(40)).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = getEmployeesByAgeUseCase.execute(40);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> e.getAge() >= 40));
        verify(employeeRepositoryPort).findByAgeGreaterThanEqual(40);
    }

    @Test
    void execute_WithNoMatches_ShouldReturnEmptyList() {
        when(employeeRepositoryPort.findByAgeGreaterThanEqual(60)).thenReturn(Arrays.asList());

        List<Employee> result = getEmployeesByAgeUseCase.execute(60);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(employeeRepositoryPort).findByAgeGreaterThanEqual(60);
    }
}
