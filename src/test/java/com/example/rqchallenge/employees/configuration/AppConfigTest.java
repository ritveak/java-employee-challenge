package com.example.rqchallenge.employees.configuration;

import com.example.rqchallenge.employees.data.ApiEmployeeDataSource;
import com.example.rqchallenge.employees.data.EmployeeDataSource;
import com.example.rqchallenge.employees.data.MockEmployeeDataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void restTemplate_ShouldCreateRestTemplateBean() {
        AppConfig appConfig = new AppConfig();
        RestTemplate restTemplate = appConfig.restTemplate();
        assertNotNull(restTemplate);
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("api")
    class ApiEmployeeDataSourceTest {
        @Autowired
        private EmployeeDataSource employeeDataSource;

        @Test
        void apiEmployeeDataSource_ShouldCreateApiEmployeeDataSourceBean() {
            assertNotNull(employeeDataSource);
            assertTrue(employeeDataSource instanceof ApiEmployeeDataSource);
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("mock")
    class MockEmployeeDataSourceTest {
        @Autowired
        private EmployeeDataSource employeeDataSource;

        @Test
        void mockEmployeeDataSource_ShouldCreateMockEmployeeDataSourceBean() {
            assertNotNull(employeeDataSource);
            assertTrue(employeeDataSource instanceof MockEmployeeDataSource);
        }
    }
}