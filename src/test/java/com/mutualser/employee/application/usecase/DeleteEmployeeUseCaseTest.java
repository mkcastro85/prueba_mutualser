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
class DeleteEmployeeUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private DeleteEmployeeUseCase deleteEmployeeUseCase;

    @BeforeEach
    void setUp() {
        deleteEmployeeUseCase = new DeleteEmployeeUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_WithExistingId_ShouldDeleteEmployee() {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepositoryPort).deleteById(1L);

        assertDoesNotThrow(() -> deleteEmployeeUseCase.execute(1L));

        verify(employeeRepositoryPort).findById(1L);
        verify(employeeRepositoryPort).deleteById(1L);
    }

    @Test
    void execute_WithNonExistingId_ShouldThrowException() {
        when(employeeRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> 
            deleteEmployeeUseCase.execute(999L));

        verify(employeeRepositoryPort).findById(999L);
        verify(employeeRepositoryPort, never()).deleteById(anyLong());
    }
}
