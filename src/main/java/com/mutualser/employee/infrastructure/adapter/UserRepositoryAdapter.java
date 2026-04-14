package com.mutualser.employee.infrastructure.adapter;

import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.domain.port.UserRepositoryPort;
import com.mutualser.employee.infrastructure.entity.UserEntity;
import com.mutualser.employee.infrastructure.mapper.UserMapper;
import com.mutualser.employee.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }
}
