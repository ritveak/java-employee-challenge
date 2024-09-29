package com.example.rqchallenge.employees.configuration;

import com.example.rqchallenge.employees.data.ApiEmployeeDataSource;
import com.example.rqchallenge.employees.data.EmployeeDataSource;
import com.example.rqchallenge.employees.data.MockEmployeeDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("!mock")
    public EmployeeDataSource apiEmployeeDataSource(RestTemplate restTemplate) {
        return new ApiEmployeeDataSource(restTemplate);
    }

    @Bean
    @Profile("mock")
    public EmployeeDataSource mockEmployeeDataSource() {
        return new MockEmployeeDataSource();
    }
}
