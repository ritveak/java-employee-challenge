package com.example.rqchallenge.employees.controller.exception;

public class EmployeeServiceException extends RuntimeException {
    public EmployeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
