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
class GetAllEmployeesUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private GetAllEmployeesUseCase getAllEmployeesUseCase;

    @BeforeEach
    void setUp() {
        getAllEmployeesUseCase = new GetAllEmployeesUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_ShouldReturnAllEmployees() {
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .gender(Employee.Gender.FEMALE)
                .age(25)
                .email("jane.smith@example.com")
                .build();

        when(employeeRepositoryPort.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = getAllEmployeesUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(employeeRepositoryPort).findAll();
    }

    @Test
    void execute_WithNoEmployees_ShouldReturnEmptyList() {
        when(employeeRepositoryPort.findAll()).thenReturn(Arrays.asList());

        List<Employee> result = getAllEmployeesUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(employeeRepositoryPort).findAll();
    }
}
