package com.example.sanitas.config; // (!) Package path 

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
                // It should allow "ALL endpoint patterns" (/api, /auth, etc.)
                registry.addMapping("/**")
                        // This is the URL of the React App (Frontend Team):
                        .allowedOrigins("http://localhost:5173") // (!) It should match the Vite server's URL!
                        // Allow these HTTP methods
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Allow all headers
                        .allowedHeaders("*")
                        // Allow cookies/authentication "in case of" use them later:
                        .allowCredentials(true);
            }
        };
    }
}