package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import com.mutualser.employee.infrastructure.repository.EmployeeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryAdapterTest {

    @Mock
    private EmployeeJpaRepository employeeJpaRepository;

    private EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @BeforeEach
    void setUp() {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter(employeeJpaRepository);
    }

    @Test
    void save_ShouldSaveAndReturnEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        EmployeeEntity savedEntity = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        when(employeeJpaRepository.save(any(EmployeeEntity.class))).thenReturn(savedEntity);

        Employee result = employeeRepositoryAdapter.save(employee);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(employeeJpaRepository).save(any(EmployeeEntity.class));
    }

    @Test
    void findById_WithExistingId_ShouldReturnEmployee() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        when(employeeJpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Employee> result = employeeRepositoryAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        verify(employeeJpaRepository).findById(1L);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        when(employeeJpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeRepositoryAdapter.findById(999L);

        assertFalse(result.isPresent());
        verify(employeeJpaRepository).findById(999L);
    }

    @Test
    void findAll_ShouldReturnAllEmployees() {
        EmployeeEntity entity1 = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(30)
                .email("john.doe@example.com")
                .build();

        EmployeeEntity entity2 = EmployeeEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .gender(Employee.Gender.FEMALE)
                .age(25)
                .email("jane.smith@example.com")
                .build();

        when(employeeJpaRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        List<Employee> result = employeeRepositoryAdapter.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeJpaRepository).findAll();
    }

    @Test
    void deleteById_ShouldCallRepository() {
        doNothing().when(employeeJpaRepository).deleteById(1L);

        employeeRepositoryAdapter.deleteById(1L);

        verify(employeeJpaRepository).deleteById(1L);
    }

    @Test
    void count_ShouldReturnCount() {
        when(employeeJpaRepository.count()).thenReturn(5L);

        long result = employeeRepositoryAdapter.count();

        assertEquals(5L, result);
        verify(employeeJpaRepository).count();
    }

    @Test
    void findByAgeGreaterThanEqual_ShouldReturnFilteredEmployees() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Employee.Gender.MALE)
                .age(45)
                .email("john.doe@example.com")
                .build();

        when(employeeJpaRepository.findByAgeGreaterThanEqual(40)).thenReturn(Arrays.asList(entity));

        List<Employee> result = employeeRepositoryAdapter.findByAgeGreaterThanEqual(40);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(45, result.get(0).getAge());
        verify(employeeJpaRepository).findByAgeGreaterThanEqual(40);
    }

    @Test
    void findByGender_ShouldReturnFilteredEmployees() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .gender(Employee.Gender.FEMALE)
                .age(30)
                .email("jane.doe@example.com")
                .build();

        when(employeeJpaRepository.findByGender(Employee.Gender.FEMALE)).thenReturn(Arrays.asList(entity));

        List<Employee> result = employeeRepositoryAdapter.findByGender(Employee.Gender.FEMALE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Employee.Gender.FEMALE, result.get(0).getGender());
        verify(employeeJpaRepository).findByGender(Employee.Gender.FEMALE);
    }
}
