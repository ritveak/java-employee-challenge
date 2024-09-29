package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.data.EmployeeDataSource;
import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDataSource employeeDataSource;

    @Autowired
    public EmployeeService(EmployeeDataSource employeeDataSource) {
        this.employeeDataSource = employeeDataSource;
    }

    public List<Employee> getAllEmployees() throws IOException {
        return employeeDataSource.getAllEmployees();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) throws IOException {
        return employeeDataSource.getEmployeesByNameSearch(searchString);
    }

    public Employee getEmployeeById(String id) throws IOException {
        return employeeDataSource.getEmployeeById(id);
    }

    public int getHighestSalaryOfEmployees() throws IOException {
        return employeeDataSource.getHighestSalaryOfEmployees();
    }

    public List<String> getTopTenHighestEarningEmployeeNames() throws IOException {
        return employeeDataSource.getTopTenHighestEarningEmployeeNames();
    }

    public Employee createEmployee(String name, String salary, String age) throws JsonProcessingException {
        return employeeDataSource.createEmployee(name, salary, age);
    }

    public String deleteEmployeeById(String id) {
        return employeeDataSource.deleteEmployeeById(id);
    }
}