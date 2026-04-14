package com.mutualser.employee.infrastructure.mapper;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.dto.EmployeeRequest;
import com.mutualser.employee.infrastructure.dto.EmployeeResponse;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    
    Employee toDomain(EmployeeEntity entity);
    
    EmployeeEntity toEntity(Employee domain);
    
    Employee requestToDomain(EmployeeRequest request);
    
    EmployeeResponse domainToResponse(Employee domain);
}
