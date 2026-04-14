package com.mutualser.employee.infrastructure.mapper;

import com.mutualser.employee.domain.model.Employee;
import com.mutualser.employee.infrastructure.dto.EmployeeRequest;
import com.mutualser.employee.infrastructure.dto.EmployeeResponse;
import com.mutualser.employee.infrastructure.entity.EmployeeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-14T07:07:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toDomain(EmployeeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.id( entity.getId() );
        employee.firstName( entity.getFirstName() );
        employee.lastName( entity.getLastName() );
        employee.gender( entity.getGender() );
        employee.age( entity.getAge() );
        employee.email( entity.getEmail() );

        return employee.build();
    }

    @Override
    public EmployeeEntity toEntity(Employee domain) {
        if ( domain == null ) {
            return null;
        }

        EmployeeEntity.EmployeeEntityBuilder employeeEntity = EmployeeEntity.builder();

        employeeEntity.id( domain.getId() );
        employeeEntity.firstName( domain.getFirstName() );
        employeeEntity.lastName( domain.getLastName() );
        employeeEntity.gender( domain.getGender() );
        employeeEntity.age( domain.getAge() );
        employeeEntity.email( domain.getEmail() );

        return employeeEntity.build();
    }

    @Override
    public Employee requestToDomain(EmployeeRequest request) {
        if ( request == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.firstName( request.getFirstName() );
        employee.lastName( request.getLastName() );
        employee.gender( request.getGender() );
        employee.age( request.getAge() );
        employee.email( request.getEmail() );

        return employee.build();
    }

    @Override
    public EmployeeResponse domainToResponse(Employee domain) {
        if ( domain == null ) {
            return null;
        }

        EmployeeResponse.EmployeeResponseBuilder employeeResponse = EmployeeResponse.builder();

        employeeResponse.id( domain.getId() );
        employeeResponse.firstName( domain.getFirstName() );
        employeeResponse.lastName( domain.getLastName() );
        employeeResponse.gender( domain.getGender() );
        employeeResponse.age( domain.getAge() );
        employeeResponse.email( domain.getEmail() );

        return employeeResponse.build();
    }
}
