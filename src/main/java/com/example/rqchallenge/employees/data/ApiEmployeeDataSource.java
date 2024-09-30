package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiEmployeeDataSource implements EmployeeDataSource {

    private static final Logger logger = LoggerFactory.getLogger(ApiEmployeeDataSource.class);

    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiEmployeeDataSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Employee> getAllEmployees() throws Exception {
        logger.info("Fetching all employees from API");
        String url = BASE_URL + "/employees";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JsonNode root = objectMapper.readTree(response.getBody());
        List<Employee> employees = Arrays.asList(objectMapper.treeToValue(root.get("data"), Employee[].class));
        logger.info("Retrieved {} employees from API", employees.size());
        return employees;
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) throws Exception {
        logger.info("Searching employees with name containing: {}", searchString);
        List<Employee> employees = getAllEmployees();
        List<Employee> filteredEmployees = employees.stream()
                .filter(e -> e.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        logger.info("Found {} employees matching the search", filteredEmployees.size());
        return filteredEmployees;
    }

    @Override
    public Employee getEmployeeById(String id) throws Exception {
        logger.info("Fetching employee with ID: {} from API", id);
        String url = BASE_URL + "/employee/" + id;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        Employee employee = objectMapper.treeToValue(root.get("data"), Employee.class);
        if (employee != null) {
            logger.info("Retrieved employee: {}", employee.getEmployeeName());
        } else {
            logger.warn("No employee found with ID: {}", id);
        }
        return employee;
    }

    @Override
    public int getHighestSalaryOfEmployees() throws Exception {
        logger.info("Calculating highest salary of employees");
        List<Employee> employees = getAllEmployees();
        int highestSalary = employees.stream()
                .mapToInt(e -> Integer.parseInt(e.getEmployeeSalary()))
                .max()
                .orElse(0);
        logger.info("Highest salary: {}", highestSalary);
        return highestSalary;
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() throws Exception {
        logger.info("Retrieving top ten highest earning employee names");
        List<Employee> employees = getAllEmployees();
        List<String> topTen = employees.stream()
                .sorted(Comparator.comparingInt((Employee e) -> Integer.parseInt(e.getEmployeeSalary())).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
        logger.info("Retrieved {} top earning employee names", topTen.size());
        return topTen;
    }

    @Override
    public Employee createEmployee(String name, String salary, String age) throws Exception {
        logger.info("Creating new employee: name={}, salary={}, age={}", name, salary, age);
        String url = BASE_URL + "/create";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("salary", salary);
        requestBody.put("age", age);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        JsonNode root = objectMapper.readTree(response.getBody());
        Employee newEmployee = objectMapper.treeToValue(root.get("data"), Employee.class);
        logger.info("Created new employee with ID: {}", newEmployee.getId());
        return newEmployee;
    }

    @Override
    public String deleteEmployeeById(String id) throws Exception{
        logger.info("Attempting to delete employee with ID: {}", id);
        String url = BASE_URL + "/delete/" + id;
        restTemplate.delete(url);
        String result = "Employee with ID " + id + " was deleted";
        logger.info(result);
        return result;
    }
}