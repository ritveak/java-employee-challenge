//package com.example.rqchallenge.employees;
//import com.example.rqchallenge.employees.model.Employee;
//import com.example.rqchallenge.employees.controller.EmployeeController;
//import com.example.rqchallenge.employees.service.EmployeeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class EmployeeControllerTest {
//
//    @Autowired
//    private EmployeeController employeeController;
//
//    private MockMvc mockMvc;
//
//    private EmployeeService employeeService;
//
//    @BeforeEach
//    void setup() {
//        // Initialize the MockMvc object
//        employeeService = Mockito.mock(EmployeeService.class);
//        employeeController = new EmployeeController(employeeService);
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//    }
//
//    @Test
//    public void testGetAllEmployees() throws Exception {
//        // Create Employee objects using setters
//        Employee employee1 = new Employee();
//        employee1.setId("1");
//        employee1.setEmployeeName("Tiger Nixon");
//        employee1.setEmployeeSalary("320800");
//        employee1.setEmployeeAge("61");
//        employee1.setProfileImage("");
//
//        Employee employee2 = new Employee();
//        employee2.setId("2");
//        employee2.setEmployeeName("Garrett Winters");
//        employee2.setEmployeeSalary("170750");
//        employee2.setEmployeeAge("63");
//        employee2.setProfileImage("");
//
//        // Mocking the service layer
//        List<Employee> employees = Arrays.asList(employee1, employee2);
//        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
//
//        // Performing the GET request
//        mockMvc.perform(get("/employees"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].employee_name").value("Tiger Nixon"))
//                .andExpect(jsonPath("$[1].employee_name").value("Garrett Winters"));
//    }
//    @Test
//    public void testGetAllEmployees_ThrowsException() throws Exception {
//        Mockito.when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("Internal Server Error"));
//
//        // Performing the GET request and expecting an internal server error
//        mockMvc.perform(get("/employees"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Internal Server Error"));
//    }
//
//    @Test
//    public void testGetEmployeeById() throws Exception {
//        // Create Employee object using setters
//        Employee employee = new Employee();
//        employee.setId("1");
//        employee.setEmployeeName("Tiger Nixon");
//        employee.setEmployeeSalary("320800");
//        employee.setEmployeeAge("61");
//        employee.setProfileImage("");
//
//        Mockito.when(employeeService.getEmployeeById(anyString())).thenReturn(employee);
//
//        // Performing the GET request
//        mockMvc.perform(get("/employees/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.employee_name").value("Tiger Nixon"));
//    }
//
//    @Test
//    public void testGetEmployeesByNameSearch() throws Exception {
//        // Create Employee objects using setters
//        Employee employee1 = new Employee();
//        employee1.setId("1");
//        employee1.setEmployeeName("Tiger Nixon");
//        employee1.setEmployeeSalary("320800");
//        employee1.setEmployeeAge("61");
//        employee1.setProfileImage("");
//
//        Employee employee2 = new Employee();
//        employee2.setId("2");
//        employee2.setEmployeeName("Garrett Winters");
//        employee2.setEmployeeSalary("170750");
//        employee2.setEmployeeAge("63");
//        employee2.setProfileImage("");
//
//        List<Employee> employees = Arrays.asList(employee1, employee2);
//        Mockito.when(employeeService.searchEmployeesByName(anyString())).thenReturn(employees);
//
//        // Performing the GET request
//        mockMvc.perform(get("/employees/search/{searchString}", "Tiger"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].employee_name").value("Tiger Nixon"));
//    }
//
//    @Test
//    public void testGetHighestSalaryOfEmployees() throws Exception {
//        int highestSalary = 320800;
//        Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(highestSalary);
//
//        // Performing the GET request
//        mockMvc.perform(get("/employees/highestSalary"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").value(highestSalary));
//    }
//
//    @Test
//    public void testGetTopTenHighestEarningEmployeeNames() throws Exception {
//        List<String> topEarnings = Arrays.asList("Tiger Nixon", "Garrett Winters");
//        Mockito.when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(topEarnings);
//
//        // Performing the GET request
//
//        mockMvc.perform(get("/employees/topTenHighestEarningEmployeeNames"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0]").value(topEarnings.get(0)))
//                .andExpect(jsonPath("$[1]").value(topEarnings.get(1)));
////        Assertions.assertEquals("[\"Tiger Nixon\",\"Garrett Winters\"]", mockMvc.perform(get("/employees/topTenHighestEarningEmployeeNames")).andReturn().getResponse().getContentAsString());
//    }
//
//    @Test
//    public void testCreateEmployee() throws Exception {
//        String employeeId = "3";
//        Map<String, String> employeeInput = new HashMap<>();
//        employeeInput.put("name", "New Employee");
//        employeeInput.put("salary", "10000");
//        employeeInput.put("age", "25");
//
//        Employee createdEmployee = new Employee();
//        createdEmployee.setId(employeeId);
//        createdEmployee.setEmployeeName("New Employee");
//        createdEmployee.setEmployeeSalary("10000");
//        createdEmployee.setEmployeeAge("25");
//        createdEmployee.setProfileImage("");
//
//        Mockito.when(employeeService.createEmployee(any(), any(), any())).thenReturn(createdEmployee);
//
//        // Performing the POST request
//        mockMvc.perform(post("/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\": \"New Employee\", \"salary\": \"10000\", \"age\": \"25\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.employee_name").value("New Employee"));
//    }
//
//    @Test
//    public void testDeleteEmployeeById() throws Exception {
//        String employeeId = "1";
//        String deletedEmployeeName = "Tiger Nixon";
//        Mockito.when(employeeService.deleteEmployeeById(employeeId)).thenReturn(deletedEmployeeName);
//
//        // Performing the DELETE request
//        mockMvc.perform(delete("/employees/{id}", employeeId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").value("Tiger Nixon"));
//    }
//}
