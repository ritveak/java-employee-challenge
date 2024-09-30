package com.example.rqchallenge.employees.service;
import com.example.rqchallenge.employees.data.EmployeeDataSource;
import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class EmployeeServiceTest {

    @Mock
    private EmployeeDataSource mockDataSource;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(mockDataSource);
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
    void testGetAllEmployees() throws Exception {
        List<Employee> mockEmployees = Arrays.asList(
                createEmployeeObject("1", "John Doe", "50000", "30", ""),
                createEmployeeObject("2", "Jane Smith", "60000", "35", "")
        );
        when(mockDataSource.getAllEmployees()).thenReturn(mockEmployees);

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
        verify(mockDataSource).getAllEmployees();
    }

    @Test
    void testGetEmployeesByNameSearch() throws Exception {
        List<Employee> mockEmployees = List.of(
                createEmployeeObject("1", "John Doe", "50000", "30", "")
        );
        when(mockDataSource.getEmployeesByNameSearch("John")).thenReturn(mockEmployees);

        List<Employee> result = employeeService.getEmployeesByNameSearch("John");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
        verify(mockDataSource).getEmployeesByNameSearch("John");
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee mockEmployee = createEmployeeObject("1", "John Doe", "50000", "30", "");
        when(mockDataSource.getEmployeeById("1")).thenReturn(mockEmployee);

        Employee result = employeeService.getEmployeeById("1");
        assertNotNull(result);
        assertEquals("John Doe", result.getEmployeeName());
        verify(mockDataSource).getEmployeeById("1");
    }
    @Test
    void testGetEmployeeById_NonExisting() throws Exception {
        Employee mockEmployee = createEmployeeObject("1", "John Doe", "50000", "30", "");
        when(mockDataSource.getEmployeeById("1")).thenReturn(mockEmployee);

        Employee result = employeeService.getEmployeeById("12");
        assertNull(result);
        verify(mockDataSource).getEmployeeById("12");
    }

    @Test
    void testGetHighestSalaryOfEmployees() throws Exception {
        when(mockDataSource.getHighestSalaryOfEmployees()).thenReturn(100000);

        int result = employeeService.getHighestSalaryOfEmployees();
        assertEquals(100000, result);
        verify(mockDataSource).getHighestSalaryOfEmployees();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> mockNames = Arrays.asList("John Doe", "Jane Smith");
        when(mockDataSource.getTopTenHighestEarningEmployeeNames()).thenReturn(mockNames);

        List<String> result = employeeService.getTopTenHighestEarningEmployeeNames();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0));
        verify(mockDataSource).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void testCreateEmployee() throws Exception {
        Employee mockEmployee = createEmployeeObject("3", "New Employee", "70000", "40", "");
        when(mockDataSource.createEmployee("New Employee", "70000", "40")).thenReturn(mockEmployee);

        Employee result = employeeService.createEmployee("New Employee", "70000", "40");
        assertNotNull(result);
        assertEquals("New Employee", result.getEmployeeName());
        verify(mockDataSource).createEmployee("New Employee", "70000", "40");
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        when(mockDataSource.deleteEmployeeById("1")).thenReturn("Employee deleted");

        String result = employeeService.deleteEmployeeById("1");
        assertEquals("Employee deleted", result);
        verify(mockDataSource).deleteEmployeeById("1");
    }
}