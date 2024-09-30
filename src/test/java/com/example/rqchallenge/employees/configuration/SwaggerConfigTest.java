package com.example.rqchallenge.employees.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

import static org.mockito.Mockito.*;

class SwaggerConfigTest {

    @Test
    void configurePathMatch_ShouldSetPatternParserToNull() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();
        PathMatchConfigurer configurer = mock(PathMatchConfigurer.class);

        swaggerConfig.configurePathMatch(configurer);

        verify(configurer).setPatternParser(null);
    }
}