package com.mutualser.employee.application.usecase;

import com.mutualser.employee.domain.exception.InvalidEmployeeDataException;
import com.mutualser.employee.domain.exception.MaxEmployeesReachedException;
import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEmployeeUseCaseTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private CreateEmployeeUseCase createEmployeeUseCase;

    @BeforeEach
    void setUp() {
        createEmployeeUseCase = new CreateEmployeeUseCase(employeeRepositoryPort);
    }

    @Test
    void execute_WithValidEmployee_ShouldSaveEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        when(employeeRepositoryPort.count()).thenReturn(0L);
        when(employeeRepositoryPort.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee result = createEmployeeUseCase.execute(employee);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(employeeRepositoryPort).count();
        verify(employeeRepositoryPort).save(employee);
    }

    @Test
    void execute_WhenMaxEmployeesReached_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        when(employeeRepositoryPort.count()).thenReturn(30L);

        assertThrows(MaxEmployeesReachedException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort).count();
        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithNullFirstName_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName(null)
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        assertThrows(InvalidEmployeeDataException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithEmptyLastName_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        assertThrows(InvalidEmployeeDataException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithNullGender_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(null)
                .age(30)
                .email("john.doe@example.com")
                .build();

        assertThrows(InvalidEmployeeDataException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithInvalidAge_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(0)
                .email("john.doe@example.com")
                .build();

        assertThrows(InvalidEmployeeDataException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }

    @Test
    void execute_WithInvalidEmail_ShouldThrowException() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("invalid-email")
                .build();

        assertThrows(InvalidEmployeeDataException.class, () -> 
            createEmployeeUseCase.execute(employee));

        verify(employeeRepositoryPort, never()).save(any(Employee.class));
    }
}
