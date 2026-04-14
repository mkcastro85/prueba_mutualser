package com.mutualser.employee.infrastructure.mapper;

import com.mutualser.employee.domain.model.User;
import com.mutualser.employee.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    User toDomain(UserEntity entity);
    
    UserEntity toEntity(User domain);
}
