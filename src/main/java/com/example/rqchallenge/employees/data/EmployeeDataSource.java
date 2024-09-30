package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import java.util.List;

public interface EmployeeDataSource {
    List<Employee> getAllEmployees() throws  Exception;
    List<Employee> getEmployeesByNameSearch(String searchString) throws Exception;
    Employee getEmployeeById(String id) throws Exception;
    int getHighestSalaryOfEmployees() throws Exception;
    List<String> getTopTenHighestEarningEmployeeNames() throws Exception;
    Employee createEmployee(String name, String salary, String age) throws Exception;
    String deleteEmployeeById(String id) throws Exception;
}