package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.EmployeeNotFoundException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private GetEmployeeUseCase getEmployeeUseCase;

    @BeforeEach
    void setUp() {
        getEmployeeUseCase = new GetEmployeeUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_WithExistingId_ShouldReturnEmployee() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = getEmployeeUseCase.execute(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Meyling", result.getFirstName());
        verify(employeeRepositoryPort).findById(1L);
    }

    @Test
    void execute_WithNonExistingId_ShouldThrowException() {
        when(employeeRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> 
            getEmployeeUseCase.execute(999L));

        verify(employeeRepositoryPort).findById(999L);
    }
}
