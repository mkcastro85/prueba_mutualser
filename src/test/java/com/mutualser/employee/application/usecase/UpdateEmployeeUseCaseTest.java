package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.EmployeeNotFoundException;
import com.mutualser.employee.domain.exception.InvalidEmployeeDataException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private UpdateEmployeeUseCase updateEmployeeUseCase;

    @BeforeEach
    void setUp() {
        updateEmployeeUseCase = new UpdateEmployeeUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_WithValidData_ShouldUpdateEmployee() {
        Employee existingEmployee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee updatedData = Employee.builder()
                .firstName("Meyling Karina")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(29)
                .email("meyling.k.castro@gmail.com")
                .build();

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepositoryPort.save(any(Employee.class))).thenReturn(existingEmployee);

        Employee result = updateEmployeeUseCase.execute(1L, updatedData);

        assertNotNull(result);
        assertEquals("Meyling Karina", result.getFirstName());
        assertEquals("Castro", result.getLastName());
        assertEquals(Employee.Gender.FEMALE, result.getGender());
        assertEquals(29, result.getAge());
        assertEquals("meyling.k.castro@gmail.com", result.getEmail());
        verify(employeeRepositoryPort).findById(1L);
        verify(employeeRepositoryPort).save(existingEmployee);
    }

    @Test
    void execute_WithNonExistingId_ShouldThrowException() {
        Employee updatedData = Employee.builder()
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> 
            updateEmployeeUseCase.execute(999L, updatedData));

        verify(employeeRepositoryPort).findById(999L);
        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithInvalidData_ShouldThrowException() {
        Employee existingEmployee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee invalidData = Employee.builder()
                .firstName("")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(existingEmployee));

        assertThrows(InvalidEmployeeDataException.class, () -> 
            updateEmployeeUseCase.execute(1L, invalidData));

        verify(employeeRepositoryPort).findById(1L);
        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }
}
