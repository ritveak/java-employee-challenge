package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MockEmployeeDataSource implements EmployeeDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MockEmployeeDataSource.class);

    private List<Employee> employees;

    public MockEmployeeDataSource() {
        employees = new ArrayList<>();
        initializeEmployees();
    }

    private void initializeEmployees() {
        employees.add(createEmployeeObject("1", "John Doe", "60000", "30", ""));
        employees.add(createEmployeeObject("2", "Jane Smith", "75000", "35", ""));
        employees.add(createEmployeeObject("3", "Mike Johnson", "55000", "28", ""));
        employees.add(createEmployeeObject("4", "Emily Brown", "80000", "40", ""));
        employees.add(createEmployeeObject("5", "David Lee", "70000", "33", ""));
        logger.info("Initialized {} employees", employees.size());
    }

    private Employee createEmployeeObject(String id, String name, String salary, String age, String profileImage) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName(name);
        employee.setEmployeeSalary(salary);
        employee.setEmployeeAge(age);
        employee.setProfileImage(profileImage);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Retrieving all employees");
        return new ArrayList<>(employees);
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        logger.info("Searching employees with name containing: {}", searchString);
        List<Employee> result = employees.stream()
                .filter(e -> e.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        logger.info("Found {} employees matching the search", result.size());
        return result;
    }

    @Override
    public Employee getEmployeeById(String id) {
        logger.info("Retrieving employee with ID: {}", id);
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getHighestSalaryOfEmployees() {
        logger.info("Calculating highest salary of employees");
        int highestSalary = employees.stream()
                .mapToInt(e -> Integer.parseInt(e.getEmployeeSalary()))
                .max()
                .orElse(0);
        logger.info("Highest salary: {}", highestSalary);
        return highestSalary;
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        logger.info("Retrieving top ten highest earning employee names from Mock Data");
        List<String> topTen = employees.stream()
                .sorted(Comparator.comparingInt((Employee e) -> Integer.parseInt(e.getEmployeeSalary())).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
        logger.info("Retrieved {} top earning employee names from Mock Data", topTen.size());
        return topTen;
    }

    @Override
    public Employee createEmployee(String name, String salary, String age) {
        logger.info("Creating new employee in Mock Data: name={}, salary={}, age={}", name, salary, age);
        String id = String.valueOf(employees.size() + 1);
        Employee newEmployee = createEmployeeObject(id, name, salary, age, "");
        employees.add(newEmployee);
        logger.info("Created new employee in Mock Data with ID: {}", id);
        return newEmployee;
    }

    @Override
    public String deleteEmployeeById(String id) {
        logger.info("Attempting to delete employee  in Mock Data with ID: {}", id);
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        String result = removed ? "Employee with ID " + id + " was deleted from Mock Data" : "Employee with ID " + id + " not found in Mock Data";
        logger.info(result);
        return result;
    }
}