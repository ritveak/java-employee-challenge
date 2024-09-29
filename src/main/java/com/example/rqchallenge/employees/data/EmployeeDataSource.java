package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import java.io.IOException;
import java.util.List;

public interface EmployeeDataSource {
    List<Employee> getAllEmployees() throws IOException;
    List<Employee> getEmployeesByNameSearch(String searchString) throws IOException;
    Employee getEmployeeById(String id) throws IOException;
    int getHighestSalaryOfEmployees() throws IOException;
    List<String> getTopTenHighestEarningEmployeeNames() throws IOException;
    Employee createEmployee(String name, String salary, String age) throws IOException;
    String deleteEmployeeById(String id);
}