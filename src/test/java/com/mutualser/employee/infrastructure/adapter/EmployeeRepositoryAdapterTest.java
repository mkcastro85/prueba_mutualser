package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import com.mutualser.employee.infrastructure.mapper.EmployeeMapper;
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

    @Mock
    private EmployeeMapper employeeMapper;

    private EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @BeforeEach
    void setUp() {
        employeeRepositoryAdapter = new EmployeeRepositoryAdapter(employeeJpaRepository, employeeMapper);
    }

    @Test
    void save_ShouldSaveAndReturnEmployee() {
        Employee employee = Employee.builder()
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        EmployeeEntity entity = EmployeeEntity.builder()
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        EmployeeEntity savedEntity = EmployeeEntity.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeMapper.toEntity(employee)).thenReturn(entity);
        when(employeeJpaRepository.save(entity)).thenReturn(savedEntity);
        when(employeeMapper.toDomain(savedEntity)).thenReturn(savedEmployee);

        Employee result = employeeRepositoryAdapter.save(employee);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Meyling", result.getFirstName());
        verify(employeeMapper).toEntity(employee);
        verify(employeeJpaRepository).save(entity);
        verify(employeeMapper).toDomain(savedEntity);
    }

    @Test
    void findById_WithExistingId_ShouldReturnEmployee() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(employeeMapper.toDomain(entity)).thenReturn(employee);

        Optional<Employee> result = employeeRepositoryAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Meyling", result.get().getFirstName());
        verify(employeeJpaRepository).findById(1L);
        verify(employeeMapper).toDomain(entity);
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
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        EmployeeEntity entity2 = EmployeeEntity.builder()
                .id(2L)
                .firstName("Roberto")
                .lastName("Martínez")
                .gender(Employee.Gender.MALE)
                .age(42)
                .email("roberto.martinez@gmail.com")
                .build();

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
                .firstName("Roberto")
                .lastName("Martínez")
                .gender(Employee.Gender.MALE)
                .age(42)
                .email("roberto.martinez@gmail.com")
                .build();

        when(employeeJpaRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(employeeMapper.toDomain(entity1)).thenReturn(employee1);
        when(employeeMapper.toDomain(entity2)).thenReturn(employee2);

        List<Employee> result = employeeRepositoryAdapter.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeJpaRepository).findAll();
        verify(employeeMapper, times(2)).toDomain(any(EmployeeEntity.class));
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
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(48)
                .email("meyling.castro@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(48)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeJpaRepository.findByAgeGreaterThanEqual(40)).thenReturn(Arrays.asList(entity));
        when(employeeMapper.toDomain(entity)).thenReturn(employee);

        List<Employee> result = employeeRepositoryAdapter.findByAgeGreaterThanEqual(40);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(48, result.get(0).getAge());
        verify(employeeJpaRepository).findByAgeGreaterThanEqual(40);
        verify(employeeMapper).toDomain(entity);
    }

    @Test
    void findByGender_ShouldReturnFilteredEmployees() {
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Meyling")
                .lastName("Castro")
                .gender(Employee.Gender.FEMALE)
                .age(28)
                .email("meyling.castro@gmail.com")
                .build();

        when(employeeJpaRepository.findByGender(Employee.Gender.FEMALE)).thenReturn(Arrays.asList(entity));
        when(employeeMapper.toDomain(entity)).thenReturn(employee);

        List<Employee> result = employeeRepositoryAdapter.findByGender(Employee.Gender.FEMALE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Employee.Gender.FEMALE, result.get(0).getGender());
        verify(employeeJpaRepository).findByGender(Employee.Gender.FEMALE);
        verify(employeeMapper).toDomain(entity);
    }
}
