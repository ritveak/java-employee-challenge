package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EmployeeControllerTest {

    @Mock
    private EmployeeService mockEmployeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(mockEmployeeService);
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
    @Test
    void testGetAllEmployees_Success() throws IOException {
        List<Employee> mockEmployees = Arrays.asList(
                createEmployeeObject("1", "John Doe", "50000", "30", ""),
                createEmployeeObject("2", "Jane Smith", "60000", "35", "")
        );
        when(mockEmployeeService.getAllEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(mockEmployeeService).getAllEmployees();
    }

    @Test
    void testGetAllEmployees_Exception() throws IOException {
        when(mockEmployeeService.getAllEmployees()).thenThrow(new IOException("API Error"));

        assertThrows(RuntimeException.class, () -> employeeController.getAllEmployees());
        verify(mockEmployeeService).getAllEmployees();
    }

    @Test
    void testGetEmployeesByNameSearch_Success() throws IOException {
        List<Employee> mockEmployees = Arrays.asList(
                createEmployeeObject("1", "John Doe", "50000", "30", "")
        );
        when(mockEmployeeService.getEmployeesByNameSearch("John")).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("John");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getEmployeeName());
        verify(mockEmployeeService).getEmployeesByNameSearch("John");
    }

    @Test
    void testGetEmployeesByNameSearch_Exception() throws IOException {
        when(mockEmployeeService.getEmployeesByNameSearch("John")).thenThrow(new IOException("Search Error"));

        assertThrows(RuntimeException.class, () -> employeeController.getEmployeesByNameSearch("John"));
        verify(mockEmployeeService).getEmployeesByNameSearch("John");
    }

    @Test
    void testGetEmployeeById_Success() throws IOException {
        Employee mockEmployee = createEmployeeObject("1", "John Doe", "50000", "30", "");
        when(mockEmployeeService.getEmployeeById("1")).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getEmployeeName());
        verify(mockEmployeeService).getEmployeeById("1");
    }

    @Test
    void testGetEmployeeById_NotFound() throws IOException {
        when(mockEmployeeService.getEmployeeById("999")).thenReturn(null);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("999");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(mockEmployeeService).getEmployeeById("999");
    }

    @Test
    void testGetEmployeeById_Exception() throws IOException {
        when(mockEmployeeService.getEmployeeById("1")).thenThrow(new IOException("Fetch Error"));

        assertThrows(RuntimeException.class, () -> employeeController.getEmployeeById("1"));
        verify(mockEmployeeService).getEmployeeById("1");
    }

    @Test
    void testGetHighestSalaryOfEmployees_Success() throws IOException {
        when(mockEmployeeService.getHighestSalaryOfEmployees()).thenReturn(100000);

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100000, response.getBody());
        verify(mockEmployeeService).getHighestSalaryOfEmployees();
    }

    @Test
    void testGetHighestSalaryOfEmployees_Exception() throws IOException {
        when(mockEmployeeService.getHighestSalaryOfEmployees()).thenThrow(new IOException("Salary Error"));

        assertThrows(RuntimeException.class, () -> employeeController.getHighestSalaryOfEmployees());
        verify(mockEmployeeService).getHighestSalaryOfEmployees();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames_Success() throws IOException {
        List<String> mockNames = Arrays.asList("John Doe", "Jane Smith");
        when(mockEmployeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(mockNames);

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0));
        verify(mockEmployeeService).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames_Exception() throws IOException {
        when(mockEmployeeService.getTopTenHighestEarningEmployeeNames()).thenThrow(new IOException("Top Earners Error"));

        assertThrows(RuntimeException.class, () -> employeeController.getTopTenHighestEarningEmployeeNames());
        verify(mockEmployeeService).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void testCreateEmployee_Success() throws IOException {
        Employee mockEmployee = createEmployeeObject("3", "New Employee", "70000", "40", "");
        when(mockEmployeeService.createEmployee("New Employee", "70000", "40")).thenReturn(mockEmployee);

        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "New Employee");
        employeeInput.put("salary", "70000");
        employeeInput.put("age", "40");

        ResponseEntity<Employee> response = employeeController.createEmployee(employeeInput);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Employee", response.getBody().getEmployeeName());
        verify(mockEmployeeService).createEmployee("New Employee", "70000", "40");
    }

    @Test
    void testCreateEmployee_Exception() throws IOException {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "New Employee");
        employeeInput.put("salary", "70000");
        employeeInput.put("age", "40");

        when(mockEmployeeService.createEmployee("New Employee", "70000", "40")).thenThrow(new IOException("Create Error"));

        assertThrows(RuntimeException.class, () -> employeeController.createEmployee(employeeInput));
        verify(mockEmployeeService).createEmployee("New Employee", "70000", "40");
    }

    @Test
    void testDeleteEmployeeById_Success() {
        when(mockEmployeeService.deleteEmployeeById("1")).thenReturn("Employee deleted");

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\"Employee deleted\"", response.getBody());
        verify(mockEmployeeService).deleteEmployeeById("1");
    }

    @Test
    void testDeleteEmployeeById_NotFound() {
        when(mockEmployeeService.deleteEmployeeById("999")).thenReturn("Employee not found");

        ResponseEntity<String> response = employeeController.deleteEmployeeById("999");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\"Employee not found\"", response.getBody());
        verify(mockEmployeeService).deleteEmployeeById("999");
    }
}