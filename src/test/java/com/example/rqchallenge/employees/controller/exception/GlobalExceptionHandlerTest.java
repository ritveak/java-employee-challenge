package com.example.rqchallenge.employees.controller.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleEmployeeNotFoundException() {
        String errorMessage = "Employee not found";
        EmployeeNotFoundException exception = new EmployeeNotFoundException(errorMessage);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleEmployeeNotFoundException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }

    @Test
    void handleEmployeeServiceException() {
        String errorMessage = "Employee service error";
        EmployeeServiceException exception = new EmployeeServiceException(errorMessage, new Exception());

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleEmployeeServiceException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
        assertEquals(errorMessage, errorResponse.getMessage());
    }

    @Test
    void handleGenericException() {
        String errorMessage = "Generic error";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleGenericException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
        assertEquals("An unexpected error occurred", errorResponse.getMessage());
    }

    @Test
    void errorResponseConstructorAndGetters() {
        int status = HttpStatus.BAD_REQUEST.value();
        String message = "Bad request";

        ErrorResponse errorResponse = new ErrorResponse(status, message);

        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
    }
}