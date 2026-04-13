package com.mutualser.employee.domain.port;

import com.mutualser.employee.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    User save(User user);
}
