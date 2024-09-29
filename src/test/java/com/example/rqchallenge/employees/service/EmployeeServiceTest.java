//package com.example.rqchallenge.employees.service;
//
//import com.example.rqchallenge.employees.model.Employee;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//public class EmployeeServiceTest {
//
//
//    private EmployeeService employeeService;
//
//    @Mock
//    private RestTemplate restTemplate;  // Mock the RestTemplate to simulate API calls
//
//    private final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//        employeeService = new EmployeeService(restTemplate);
//    }
//
//    // Test getAllEmployees()
//    @Test
//    public void testGetAllEmployees() throws IOException {
//        // Mock response JSON
//        String jsonResponse = "{\n" +
//                "                \"status\": \"success\",\n" +
//                "                \"data\": [\n" +
//                "                    {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\"},\n" +
//                "                    {\"id\": \"2\", \"employee_name\": \"Jane Smith\", \"employee_salary\": \"60000\", \"employee_age\": \"40\", \"profile_image\": \"\"}\n" +
//                "                ]\n" +
//                "            }";
//
//        // Set up the mocked response for the API call
//        Mockito.when(restTemplate.getForEntity(BASE_URL + "/employees", String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        // Call the method under test
//        List<Employee> employees = employeeService.getAllEmployees();
//
//        // Assertions
//        Assertions.assertEquals(2, employees.size());
//        Assertions.assertEquals("John Doe", employees.get(0).getEmployeeName());
//        Assertions.assertEquals("Jane Smith", employees.get(1).getEmployeeName());
//    }
//
//    // Test searchEmployeesByName()
//    @Test
//    public void testSearchEmployeesByName() throws IOException {
//        // Mock the same response as in getAllEmployees()
//        String jsonResponse = "    {\n" +
//                "                \"status\": \"success\",\n" +
//                "                \"data\": [\n" +
//                "                    {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\"},\n" +
//                "                    {\"id\": \"2\", \"employee_name\": \"Jane Smith\", \"employee_salary\": \"60000\", \"employee_age\": \"40\", \"profile_image\": \"\"}\n" +
//                "                ]\n" +
//                "            }";
//
//        Mockito.when(restTemplate.getForEntity(BASE_URL + "/employees", String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        // Call the method with search string "John"
//        List<Employee> employees = employeeService.searchEmployeesByName("John");
//
//        // Assertions
//        Assertions.assertEquals(1, employees.size());
//        Assertions.assertEquals("John Doe", employees.get(0).getEmployeeName());
//
//        // Test when there are no matches
//        List<Employee> noMatchEmployees = employeeService.searchEmployeesByName("NotExistingName");
//        Assertions.assertEquals(0, noMatchEmployees.size());
//    }
//
//    // Test getEmployeeById()
//    @Test
//    public void testGetEmployeeById() throws IOException {
//        // Mock response for a specific employee ID
//        String jsonResponse = "{\n" +
//                "                \"status\": \"success\",\n" +
//                "                \"data\": {\n" +
//                "                    \"id\": \"1\",\n" +
//                "                    \"employee_name\": \"John Doe\",\n" +
//                "                    \"employee_salary\": \"50000\",\n" +
//                "                    \"employee_age\": \"30\",\n" +
//                "                    \"profile_image\": \"\"\n" +
//                "                }\n" +
//                "            }";
//
//        Mockito.when(restTemplate.getForEntity(BASE_URL + "/employee/1", String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        Employee employee = employeeService.getEmployeeById("1");
//
//        // Assertions
//        Assertions.assertNotNull(employee);
//        Assertions.assertEquals("John Doe", employee.getEmployeeName());
//    }
//
//    // Test getHighestSalaryOfEmployees()
//    @Test
//    public void testGetHighestSalaryOfEmployees() throws IOException {
//        String jsonResponse = "  {\n" +
//                "                \"status\": \"success\",\n" +
//                "                \"data\": [\n" +
//                "                    {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\"},\n" +
//                "                    {\"id\": \"2\", \"employee_name\": \"Jane Smith\", \"employee_salary\": \"60000\", \"employee_age\": \"40\", \"profile_image\": \"\"}\n" +
//                "                ]\n" +
//                "            }";
//
//        Mockito.when(restTemplate.getForEntity(BASE_URL + "/employees", String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        int highestSalary = employeeService.getHighestSalaryOfEmployees();
//
//        // Assertions
//        Assertions.assertEquals(60000, highestSalary);
//    }
//
//    // Test getTopTenHighestEarningEmployeeNames()
//    @Test
//    public void testGetTopTenHighestEarningEmployeeNames() throws IOException {
//        String jsonResponse = "{\n" +
//                "                \"status\": \"success\",\n" +
//                "                \"data\": [\n" +
//                "                    {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": \"50000\", \"employee_age\": \"30\", \"profile_image\": \"\"},\n" +
//                "                    {\"id\": \"2\", \"employee_name\": \"Jane Smith\", \"employee_salary\": \"60000\", \"employee_age\": \"40\", \"profile_image\": \"\"},\n" +
//                "                    {\"id\": \"3\", \"employee_name\": \"Foo Bar\", \"employee_salary\": \"70000\", \"employee_age\": \"50\", \"profile_image\": \"\"}\n" +
//                "                ]\n" +
//                "            }";
//
//        Mockito.when(restTemplate.getForEntity(BASE_URL + "/employees", String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        List<String> topEmployees = employeeService.getTopTenHighestEarningEmployeeNames();
//
//        // Assertions
//        Assertions.assertEquals(3, topEmployees.size());
//        Assertions.assertEquals("Foo Bar", topEmployees.get(0));  // Highest salary first
//    }
//
//    // Test createEmployee()
//    @Test
//    public void testCreateEmployee() throws IOException {
//        // Mock JSON response for employee creation
//        String jsonResponse = "{\n" +
//                "  \"status\": \"success\",\n" +
//                "  \"data\": {\n" +
//                "    \"employee_name\": \"New Employee\",\n" +
//                "    \"employee_salary\": \"10000\",\n" +
//                "    \"employee_age\": \"25\",\n" +
//                "    \"id\": \"3\"\n" +
//                "  }\n" +
//                "}";
//
//        // Request body map using the expected keys as per the API
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("name", "New Employee"); // Key matches API requirement
//        requestBody.put("salary", "10000");      // Key matches API requirement
//        requestBody.put("age", "25");            // Key matches API requirement
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Create HttpEntity with request body and headers
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
//
//        // Mock the RestTemplate call to return the mocked JSON response
//        Mockito.when(restTemplate.postForEntity(BASE_URL + "/create", entity, String.class))
//                .thenReturn(new ResponseEntity<>(jsonResponse, HttpStatus.OK));
//
//        // Call the createEmployee method
//        Employee employee = employeeService.createEmployee("New Employee", "10000", "25");
//
//        // Assertions
//        Assertions.assertNotNull(employee);
//        Assertions.assertEquals("New Employee", employee.getEmployeeName());
//        Assertions.assertEquals("10000", employee.getEmployeeSalary());
//        Assertions.assertEquals("25", employee.getEmployeeAge());
//    }
//
//    // Test deleteEmployeeById()
//    @Test
//    public void testDeleteEmployeeById() {
//        String successMessage = "successfully! deleted Record";
//
//        Mockito.doNothing().when(restTemplate).delete(BASE_URL + "/delete/1");
//
//        String result = employeeService.deleteEmployeeById("1");
//
//        // Assertions
//        Assertions.assertEquals("Employee with ID 1 was deleted", result);
//    }
//}
