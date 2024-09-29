package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.data.EmployeeDataSource;
import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeDataSource employeeDataSource;

    @Autowired
    public EmployeeService(EmployeeDataSource employeeDataSource) {
        this.employeeDataSource = employeeDataSource;
    }

    public List<Employee> getAllEmployees() throws IOException {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeDataSource.getAllEmployees();
        logger.info("Retrieved {} employees", employees.size());
        return employees;
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) throws IOException {
        logger.info("Searching employees with name containing: {}", searchString);
        List<Employee> employees = employeeDataSource.getEmployeesByNameSearch(searchString);
        logger.info("Found {} employees matching the search", employees.size());
        return employees;
    }

    public Employee getEmployeeById(String id) throws IOException {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeDataSource.getEmployeeById(id);
        if (employee != null) {
            logger.info("Retrieved employee: {}", employee.getEmployeeName());
        } else {
            logger.warn("No employee found with ID: {}", id);
        }
        return employee;
    }

    public int getHighestSalaryOfEmployees() throws IOException {
        logger.info("Calculating highest salary of employees");
        int highestSalary = employeeDataSource.getHighestSalaryOfEmployees();
        logger.info("Highest salary: {}", highestSalary);
        return highestSalary;
    }

    public List<String> getTopTenHighestEarningEmployeeNames() throws IOException {
        logger.info("Retrieving top ten highest earning employee names");
        List<String> topTen = employeeDataSource.getTopTenHighestEarningEmployeeNames();
        logger.info("Retrieved {} top earning employee names", topTen.size());
        return topTen;
    }

    public Employee createEmployee(String name, String salary, String age) throws JsonProcessingException {
        logger.info("Creating new employee: name={}, salary={}, age={}", name, salary, age);
        Employee newEmployee = employeeDataSource.createEmployee(name, salary, age);
        logger.info("Created new employee with ID: {}", newEmployee.getId());
        return newEmployee;
    }

    public String deleteEmployeeById(String id) {
        logger.info("Attempting to delete employee with ID: {}", id);
        String result = employeeDataSource.deleteEmployeeById(id);
        logger.info(result);
        return result;
    }
}