package com.example.rqchallenge.employees.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Ensure there's a valid pattern parser or set to null to use default
        configurer.setPatternParser(null);  // Use the default AntPathMatcher
    }
}