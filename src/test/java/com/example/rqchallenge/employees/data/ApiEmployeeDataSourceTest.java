package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApiEmployeeDataSourceTest {

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private ApiEmployeeDataSource apiEmployeeDataSource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        apiEmployeeDataSource = new ApiEmployeeDataSource(restTemplate);
        objectMapper = new ObjectMapper();
    }

    // Positive Test Cases

    @Test
    public void getAllEmployees_Success() throws IOException {
        List<Employee> expectedEmployees = createSampleEmployees();
        String jsonResponse = objectMapper.writeValueAsString(expectedEmployees);

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));

        List<Employee> actualEmployees = apiEmployeeDataSource.getAllEmployees();

        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    public void getEmployeesByNameSearch_Success() throws IOException {
        List<Employee> employees = createSampleEmployees();
        String searchString = "John";

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        List<Employee> filteredEmployees = apiEmployeeDataSource.getEmployeesByNameSearch(searchString);

        assertTrue(filteredEmployees.stream().allMatch(e -> e.getEmployeeName().toLowerCase().contains(searchString.toLowerCase())));
    }

    @Test
    public void getEmployeeById_Success() throws IOException {
        Employee expectedEmployee = createSampleEmployees().get(0);
        String id = expectedEmployee.getId();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(expectedEmployee), HttpStatus.OK));

        Employee actualEmployee = apiEmployeeDataSource.getEmployeeById(id);

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    public void getHighestSalaryOfEmployees_Success() throws IOException {
        List<Employee> employees = createSampleEmployees();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        int actualHighestSalary = apiEmployeeDataSource.getHighestSalaryOfEmployees();

        assertEquals("909", actualHighestSalary);
    }

    @Test
    public void getTopTenHighestEarningEmployeeNames_Success() throws IOException {
        List<Employee> employees = createSampleEmployees();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        List<String> actualTopTen = apiEmployeeDataSource.getTopTenHighestEarningEmployeeNames();

        assertEquals("expectedTopTen", actualTopTen.get(0));
    }

    @Test
    public void createEmployee_Success() throws IOException, JsonProcessingException {
        Employee expectedEmployee = createEmployeeObject("1","Test Employee", "100000", "30");
        String jsonResponse = objectMapper.writeValueAsString(expectedEmployee);

        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.CREATED));

        Employee actualEmployee = apiEmployeeDataSource.createEmployee(expectedEmployee.getEmployeeName(), expectedEmployee.getEmployeeSalary(), expectedEmployee.getEmployeeAge());

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    public void deleteEmployeeById_Success() {
        String id = "123";

        String result = apiEmployeeDataSource.deleteEmployeeById(id);

        assertEquals("Employee with ID " + id + " was deleted", result);
    }

    // Negative Test Cases

    @Test
    public void getAllEmployees_NetworkError() throws IOException {
        when(restTemplate.getForEntity(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Network error"));

        assertThrows(HttpClientErrorException.class, () -> apiEmployeeDataSource.getAllEmployees());
    }

    @Test
    public void getEmployeesByNameSearch_EmptyList() throws IOException {
        List<Employee> employees = new ArrayList<>();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        List<Employee> filteredEmployees = apiEmployeeDataSource.getEmployeesByNameSearch("John");

        assertTrue(filteredEmployees.isEmpty());
    }

    @Test
    public void getEmployeeById_NotFound() throws IOException {
        String id = "123";

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        Employee employee = apiEmployeeDataSource.getEmployeeById(id);

        assertNull(employee);
    }

    @Test
    public void getHighestSalaryOfEmployees_EmptyList() throws IOException {
        List<Employee> employees = new ArrayList<>();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        int highestSalary = apiEmployeeDataSource.getHighestSalaryOfEmployees();

        assertEquals(0, highestSalary);
    }

    @Test
    public void getTopTenHighestEarningEmployeeNames_EmptyList() throws IOException {
        List<Employee> employees = new ArrayList<>();

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(objectMapper.writeValueAsString(employees), HttpStatus.OK));

        List<String> topTen = apiEmployeeDataSource.getTopTenHighestEarningEmployeeNames();

        assertTrue(topTen.isEmpty());
    }

    @Test
    public void createEmployee_BadRequest() throws IOException, JsonProcessingException {
        Employee invalidEmployee = createEmployeeObject("","", "", "");

        when(restTemplate.postForEntity(anyString(), any(), any())).thenReturn(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

        assertThrows(HttpClientErrorException.class, () -> apiEmployeeDataSource.createEmployee(invalidEmployee.getEmployeeName(), invalidEmployee.getEmployeeSalary(), invalidEmployee.getEmployeeAge()));
    }

    @Test
    public void deleteEmployeeById_NotFound() {
        String id = "123";

        assertThrows(HttpClientErrorException.class, () -> apiEmployeeDataSource.deleteEmployeeById(id));
    }

    // Helper Methods

    private List<Employee> createSampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployeeObject("1","John Doe", "50000", "30"));
        employees.add(createEmployeeObject("1","Jane Smith", "60000", "25"));
        employees.add(createEmployeeObject("1","Mike Johnson", "70000", "35"));
        return employees;
    }

    private Employee createEmployeeObject(String id, String name, String salary, String age) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmployeeName(name);
        employee.setEmployeeSalary(salary);
        employee.setEmployeeAge(age);
        return employee;
    }
}
