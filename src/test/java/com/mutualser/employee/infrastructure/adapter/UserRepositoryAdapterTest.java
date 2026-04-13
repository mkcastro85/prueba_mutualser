package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.infrastructure.entity.UserEntity;
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

    private UserRepositoryAdapter userRepositoryAdapter;

    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UserRepositoryAdapter(userJpaRepository);
    }

    @Test
    void findByUsername_WithExistingUsername_ShouldReturnUser() {
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        when(userJpaRepository.findByUsername("admin")).thenReturn(Optional.of(entity));

        Optional<User> result = userRepositoryAdapter.findByUsername("admin");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());
        verify(userJpaRepository).findByUsername("admin");
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
                .password("encodedPassword")
                .build();

        UserEntity savedEntity = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .build();

        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = userRepositoryAdapter.save(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("admin", result.getUsername());
        verify(userJpaRepository).save(any(UserEntity.class));
    }
}
