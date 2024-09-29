package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService{

    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate;
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() throws IOException {
        String url = BASE_URL + "/employees";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Deserialize JSON and extract the data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        List<Employee> employees = Arrays.asList(objectMapper.treeToValue(root.get("data"), Employee[].class));
        return employees;
    }

    // Search employees by name
    public List<Employee> getEmployeesByNameSearch(String searchString) throws IOException {
        List<Employee> employees = getAllEmployees();
        return employees.stream()
                .filter(e -> e.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get employee by ID
    public Employee getEmployeeById(String id) throws IOException {
        String url = BASE_URL + "/employee/" + id;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        return objectMapper.treeToValue(root.get("data"), Employee.class);
    }

    // Get highest salary
    public int getHighestSalaryOfEmployees() throws IOException {
        List<Employee> employees = getAllEmployees();
        return employees.stream()
                .mapToInt(e -> Integer.parseInt(e.getEmployeeSalary()))
                .max()
                .orElse(0);
    }

    // Get top 10 highest earning employees
    public List<String> getTopTenHighestEarningEmployeeNames() throws IOException {
        List<Employee> employees = getAllEmployees();
        return employees.stream()
                .sorted(Comparator.comparingInt((Employee e) -> Integer.parseInt(e.getEmployeeSalary())).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
    }

    // Create employee
    public Employee createEmployee(String name, String salary, String age) throws JsonProcessingException {
        String url = BASE_URL + "/create";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("salary", salary);
        requestBody.put("age", age);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        return objectMapper.treeToValue(root.get("data"), Employee.class);
    }

    // Delete employee by ID
    public String deleteEmployeeById(String id) {
        String url = BASE_URL + "/delete/" + id;
        restTemplate.delete(url);
        return "Employee with ID " + id + " was deleted";
    }
}
