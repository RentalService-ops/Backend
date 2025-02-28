package com.example.RentalService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")  // ✅ Set Frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ Allow OPTIONS for preflight
                        .allowedHeaders("Authorization", "Content-Type", "X-Requested-With")  // ✅ Explicitly set headers
                        .exposedHeaders("Set-Cookie")  // ✅ Allows frontend to read the Set-Cookie header
                        .allowCredentials(true); // ✅ Must be true for cookies
            }
        };
    }
}

