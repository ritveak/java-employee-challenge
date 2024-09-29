package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MockEmployeeDataSourceTest {

    private MockEmployeeDataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new MockEmployeeDataSource();
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = dataSource.getAllEmployees();
        assertEquals(5, employees.size());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        List<Employee> employees = dataSource.getEmployeesByNameSearch("John");
        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
        assertEquals("Mike Johnson", employees.get(1).getEmployeeName());

        employees = dataSource.getEmployeesByNameSearch("oh");
        assertEquals(2, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
        assertEquals("Mike Johnson", employees.get(1).getEmployeeName());

        employees = dataSource.getEmployeesByNameSearch("NonExistent");
        assertTrue(employees.isEmpty());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = dataSource.getEmployeeById("1");
        assertNotNull(employee);
        assertEquals("John Doe", employee.getEmployeeName());

        employee = dataSource.getEmployeeById("999");
        assertNull(employee);
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        int highestSalary = dataSource.getHighestSalaryOfEmployees();
        assertEquals(80000, highestSalary);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<String> topTen = dataSource.getTopTenHighestEarningEmployeeNames();
        assertEquals(5, topTen.size());
        assertEquals("Emily Brown", topTen.get(0));
        assertEquals("Jane Smith", topTen.get(1));
    }

    @Test
    void testCreateEmployee() {
        Employee newEmployee = dataSource.createEmployee("Test Employee", "50000", "25");
        assertNotNull(newEmployee);
        assertEquals("6", newEmployee.getId());
        assertEquals("Test Employee", newEmployee.getEmployeeName());
        assertEquals("50000", newEmployee.getEmployeeSalary());
        assertEquals("25", newEmployee.getEmployeeAge());
    }

    @Test
    void testDeleteEmployeeById() {
        String result = dataSource.deleteEmployeeById("1");
        assertEquals("Employee with ID 1 was deleted from Mock Data", result);

        result = dataSource.deleteEmployeeById("999");
        assertEquals("Employee with ID 999 not found in Mock Data", result);
    }
}