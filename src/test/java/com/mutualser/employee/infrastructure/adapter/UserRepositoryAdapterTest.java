package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.infrastructure.entity.UserEntity;
import com.mutualser.employee.infrastructure.mapper.UserMapper;
import com.mutualser.employee.infrastructure.repository.UserJpaRepository;
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
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    private UserRepositoryAdapter userRepositoryAdapter;

    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UserRepositoryAdapter(userJpaRepository, userMapper);
    }

    @Test
    void findByUsername_WithExistingUsername_ShouldReturnUser() {
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        when(userJpaRepository.findByUsername("admin")).thenReturn(Optional.of(entity));
        when(userMapper.toDomain(entity)).thenReturn(expectedUser);

        Optional<User> result = userRepositoryAdapter.findByUsername("admin");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());
        verify(userJpaRepository).findByUsername("admin");
        verify(userMapper).toDomain(entity);
    }

    @Test
    void findByUsername_WithNonExistingUsername_ShouldReturnEmpty() {
        when(userJpaRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryAdapter.findByUsername("unknown");

        assertFalse(result.isPresent());
        verify(userJpaRepository).findByUsername("unknown");
    }

    @Test
    void save_ShouldSaveAndReturnUser() {
        User user = User.builder()
                .username("admin")
                .password("password123")
                .build();

        UserEntity entity = UserEntity.builder()
                .username("admin")
                .password("password123")
                .build();

        UserEntity savedEntity = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("password123")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .username("admin")
                .password("password123")
                .build();

        when(userMapper.toEntity(user)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(savedEntity);
        when(userMapper.toDomain(savedEntity)).thenReturn(savedUser);

        User result = userRepositoryAdapter.save(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("admin", result.getUsername());
        verify(userMapper).toEntity(user);
        verify(userJpaRepository).save(entity);
        verify(userMapper).toDomain(savedEntity);
    }
}
