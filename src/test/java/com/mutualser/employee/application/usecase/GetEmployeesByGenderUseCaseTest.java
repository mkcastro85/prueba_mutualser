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
class GetEmployeesByGenderUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private GetEmployeesByGenderUseCase getEmployeesByGenderUseCase;

    @BeforeEach
    void setUp() {
        getEmployeesByGenderUseCase = new GetEmployeesByGenderUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_ShouldReturnEmployeesWithSpecificGender() {
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Patricia")
                .lastName("Gómez")
                .gender(Employee.Gender.FEMALE)
                .age(32)
                .email("patricia.gomez@gmail.com")
                .build();

        when(employeeRepositoryPort.findByGender(Employee.Gender.FEMALE))
                .thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = getEmployeesByGenderUseCase.execute(Employee.Gender.FEMALE);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> e.getGender() == Employee.Gender.FEMALE));
        verify(employeeRepositoryPort).findByGender(Employee.Gender.FEMALE);
    }

    @Test
    void execute_WithNoMatches_ShouldReturnEmptyList() {
        when(employeeRepositoryPort.findByGender(Employee.Gender.MALE)).thenReturn(Arrays.asList());

        List<Employee> result = getEmployeesByGenderUseCase.execute(Employee.Gender.MALE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(employeeRepositoryPort).findByGender(Employee.Gender.MALE);
    }
}
