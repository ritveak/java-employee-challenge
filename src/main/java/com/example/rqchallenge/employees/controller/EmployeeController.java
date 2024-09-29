package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = null;
        try {
            employees = employeeService.getAllEmployees();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        List<Employee> employees = null;
        try {
            employees = employeeService.getEmployeesByNameSearch(searchString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = null;
        try {
            employee = employeeService.getEmployeeById(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        int highestSalary = 0;
        try {
            highestSalary = employeeService.getHighestSalaryOfEmployees();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> topEmployees = null;
        try {
            topEmployees = employeeService.getTopTenHighestEarningEmployeeNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(topEmployees);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        String name = (String) employeeInput.get("name");
        String salary = (String) employeeInput.get("salary");
        String age = (String) employeeInput.get("age");

        Employee employee = null;
        try {
            employee = employeeService.createEmployee(name, salary, age);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        String result = employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("\"" + result + "\"");
    }
}
