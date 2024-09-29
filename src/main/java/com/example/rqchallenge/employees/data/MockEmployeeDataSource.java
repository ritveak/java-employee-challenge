package com.example.rqchallenge.employees.data;


import com.example.rqchallenge.employees.model.Employee;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MockEmployeeDataSource implements EmployeeDataSource {

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
        return new ArrayList<>(employees);
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        return employees.stream()
                .filter(e -> e.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getHighestSalaryOfEmployees() {
        return employees.stream()
                .mapToInt(e -> Integer.parseInt(e.getEmployeeSalary()))
                .max()
                .orElse(0);
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        return employees.stream()
                .sorted(Comparator.comparingInt((Employee e) -> Integer.parseInt(e.getEmployeeSalary())).reversed())
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(String name, String salary, String age) {
        String id = String.valueOf(employees.size() + 1);
        Employee newEmployee = createEmployeeObject(id, name, salary, age, "");
        employees.add(newEmployee);
        return newEmployee;
    }

    @Override
    public String deleteEmployeeById(String id) {
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        return removed ? "Employee with ID " + id + " was deleted" : "Employee with ID " + id + " not found";
    }
}