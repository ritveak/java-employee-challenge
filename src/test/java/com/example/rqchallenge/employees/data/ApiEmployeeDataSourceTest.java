package com.example.rqchallenge.employees.data;

import com.example.rqchallenge.employees.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiEmployeeDataSourceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiEmployeeDataSource dataSource;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllEmployees_Success() throws Exception {
        String jsonResponse = "{\"data\":[{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Employee> result = dataSource.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
    }

    @Test
    void getAllEmployees_EmptyResponse() throws Exception {
        String jsonResponse = "{\"data\":[]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Employee> result = dataSource.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllEmployees_ApiError() {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> dataSource.getAllEmployees());
    }

    @Test
    void getEmployeesByNameSearch_Success() throws Exception {
        String jsonResponse = "{\"data\":[{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Employee> result = dataSource.getEmployeesByNameSearch("John");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getEmployeeName());
    }

    @Test
    void getEmployeesByNameSearch_NoMatch() throws Exception {
        String jsonResponse = "{\"data\":[{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Employee> result = dataSource.getEmployeesByNameSearch("Bob");

        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeeById_Success() throws Exception {
        String jsonResponse = "{\"data\":{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Employee result = dataSource.getEmployeeById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John Doe", result.getEmployeeName());
    }

    @Test
    void getEmployeeById_NotFound() throws Exception {
        String jsonResponse = "{\"data\":null}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        Employee result = dataSource.getEmployeeById("999");

        assertNull(result);
    }

    @Test
    void getEmployeeById_ApiError() {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> dataSource.getEmployeeById("1"));
    }

    @Test
    void getHighestSalaryOfEmployees_Success() throws Exception {
        String jsonResponse = "{\"data\":[{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        int result = dataSource.getHighestSalaryOfEmployees();

        assertEquals(50000, result);
    }

    @Test
    void getHighestSalaryOfEmployees_NoEmployees() throws Exception {
        String jsonResponse = "{\"data\":[]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        int result = dataSource.getHighestSalaryOfEmployees();

        assertEquals(0, result);
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_Success() throws Exception {
        String jsonResponse = "{\"data\":[" +
                "{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}," +
                "{\"id\":\"2\",\"employee_name\":\"Bob Johnson\",\"employee_salary\":\"60000\",\"employee_age\":\"40\",\"profile_image\":\"\"}," +
                "{\"id\":\"3\",\"employee_name\":\"Jane Smith\",\"employee_salary\":\"70000\",\"employee_age\":\"50\",\"profile_image\":\"\"}" +
                "]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<String> result = dataSource.getTopTenHighestEarningEmployeeNames();

        assertEquals(3, result.size());
        assertEquals("Jane Smith", result.get(0));
        assertEquals("Bob Johnson", result.get(1));
        assertEquals("John Doe", result.get(2));
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_LessThanTen() throws Exception {
        String jsonResponse = "{\"data\":[" +
                "{\"id\":\"1\",\"employee_name\":\"John Doe\",\"employee_salary\":\"50000\",\"employee_age\":\"30\",\"profile_image\":\"\"}," +
                "{\"id\":\"2\",\"employee_name\":\"Bob Johnson\",\"employee_salary\":\"60000\",\"employee_age\":\"40\",\"profile_image\":\"\"}," +
                "{\"id\":\"3\",\"employee_name\":\"Jane Smith\",\"employee_salary\":\"74000\",\"employee_age\":\"50\",\"profile_image\":\"\"}," +
                "{\"id\":\"4\",\"employee_name\":\"Jade Smith\",\"employee_salary\":\"345333\",\"employee_age\":\"50\",\"profile_image\":\"\"}," +
                "{\"id\":\"5\",\"employee_name\":\"Jack Smith\",\"employee_salary\":\"54000\",\"employee_age\":\"50\",\"profile_image\":\"\"}," +
                "{\"id\":\"6\",\"employee_name\":\"Smith\",\"employee_salary\":\"80000\",\"employee_age\":\"50\",\"profile_image\":\"\"}," +
                "{\"id\":\"7\",\"employee_name\":\"T Smith\",\"employee_salary\":\"78000\",\"employee_age\":\"50\",\"profile_image\":\"\"}," +
                "{\"id\":\"8\",\"employee_name\":\"Kabe Smith\",\"employee_salary\":\"23000\",\"employee_age\":\"23\",\"profile_image\":\"\"}," +
                "{\"id\":\"9\",\"employee_name\":\"Jacob Smith\",\"employee_salary\":\"23400\",\"employee_age\":\"24\",\"profile_image\":\"\"}," +
                "{\"id\":\"10\",\"employee_name\":\"Amit\",\"employee_salary\":\"90000\",\"employee_age\":\"34\",\"profile_image\":\"\"}," +
                "{\"id\":\"11\",\"employee_name\":\"Anjani Smith\",\"employee_salary\":\"120000\",\"employee_age\":\"50\",\"profile_image\":\"\"}" +
                "]}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);


        List<String> result = dataSource.getTopTenHighestEarningEmployeeNames();

        assertEquals(10, result.size());
        assertEquals("Jade Smith", result.get(0));
        assertEquals("Anjani Smith", result.get(1));
        assertEquals("Amit", result.get(2));
        assertEquals("Smith", result.get(3));
        assertEquals("T Smith", result.get(4));
        assertEquals("Jane Smith", result.get(5));
        assertEquals("Bob Johnson", result.get(6));
        assertEquals("Jack Smith", result.get(7));
        assertEquals("John Doe", result.get(8));
        assertEquals("Jacob Smith", result.get(9));
    }

    @Test
    void createEmployee_Success() throws Exception {
        String jsonResponse = "{\"data\":{\"id\":\"3\",\"employee_name\":\"Alice Brown\",\"employee_salary\":\"70000\",\"employee_age\":\"28\",\"profile_image\":\"\"}}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(responseEntity);

        Employee result = dataSource.createEmployee("Alice Brown", "70000", "28");

        assertNotNull(result);
        assertEquals("3", result.getId());
        assertEquals("Alice Brown", result.getEmployeeName());
        assertEquals("70000", result.getEmployeeSalary());
        assertEquals("28", result.getEmployeeAge());
    }

    @Test
    void createEmployee_ApiError() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(Exception.class, () -> dataSource.createEmployee("Alice Brown", "70000", "28"));
    }

    @Test
    void deleteEmployeeById_Success() throws Exception {
        doNothing().when(restTemplate).delete(anyString());

        String result = dataSource.deleteEmployeeById("1");

        assertEquals("Employee with ID 1 was deleted", result);
        verify(restTemplate, times(1)).delete(anyString());
    }

    @Test
    void deleteEmployeeById_ApiError() {
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).delete(anyString());

        assertThrows(Exception.class, () -> dataSource.deleteEmployeeById("999"));
    }


}