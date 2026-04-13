package com.mutualser.employee.domain.exception;

public class MaxEmployeesReachedException extends RuntimeException {
    public MaxEmployeesReachedException(String message) {
        super(message);
    }
}
