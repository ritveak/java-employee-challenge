package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.controller.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.controller.exception.EmployeeServiceException;
import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements IEmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        logger.info("EmployeeController initialized");
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Received request to get all employees");
        List<Employee> employees;
        try {
            employees = employeeService.getAllEmployees();
            logger.info("Retrieved {} employees", employees.size());
        } catch (Exception e) {
            logger.error("Error occurred while getting all employees", e);
            throw new EmployeeServiceException("Failed to get all employees", e);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        logger.info("Received request to search employees by name: {}", searchString);
        List<Employee> employees;
        try {
            employees = employeeService.getEmployeesByNameSearch(searchString);
            logger.info("Found {} employees matching the search string: {}", employees.size(), searchString);
        } catch (Exception e) {
            logger.error("Error occurred while searching employees by name: {}", searchString, e);
            throw new EmployeeNotFoundException("Failed to search employees with name containing "+searchString);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("Received request to get employee by ID: {}", id);
        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                logger.info("Retrieved employee with ID: {}", id);
            } else {
                logger.warn("No employee found with ID: {}", id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting employee by ID: {}", id, e);
            throw new EmployeeNotFoundException("Failed to get employee with ID "+id);
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Received request to get highest salary of employees");
        int highestSalary;
        try {
            highestSalary = employeeService.getHighestSalaryOfEmployees();
            logger.info("Highest salary of employees: {}", highestSalary);
        } catch (Exception e) {
            logger.error("Error occurred while getting highest salary of employees", e);
            throw new EmployeeServiceException("Failed to get highest salary of employees", e);
        }
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Received request to get top ten highest earning employee names");
        List<String> topEmployees;
        try {
            topEmployees = employeeService.getTopTenHighestEarningEmployeeNames();
            logger.info("Retrieved {} top earning employee names", topEmployees.size());
        } catch (Exception e) {
            logger.error("Error occurred while getting top ten highest earning employee names", e);
            throw new EmployeeServiceException("Failed to get top ten highest earning employee names", e);
        }
        return ResponseEntity.ok(topEmployees);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        String name = (String) employeeInput.get("name");
        String salary = (String) employeeInput.get("salary");
        String age = (String) employeeInput.get("age");
        logger.info("Received request to create employee: name={}, salary={}, age={}", name, salary, age);

        Employee employee;
        try {
            employee = employeeService.createEmployee(name, salary, age);
            logger.info("Created new employee with ID: {}", employee.getId());
        } catch (Exception e) {
            logger.error("Error occurred while creating employee", e);
            throw new EmployeeServiceException("Failed to create employee", e);
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        logger.info("Received request to delete employee with ID: {}", id);
        String result = null;
        try {
            result = employeeService.deleteEmployeeById(id);
        } catch (Exception e) {
            logger.info("Error occurred while deleting employee with id ");
            throw new EmployeeServiceException("Error occurred while deleting employee with id "+id, e);
        }
        logger.info("Delete operation result: {}", result);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("\"" + result + "\"");
    }
}