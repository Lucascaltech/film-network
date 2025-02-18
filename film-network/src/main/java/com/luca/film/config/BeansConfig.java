package com.luca.film.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration class that defines application-wide beans.
 */
@Configuration
public class BeansConfig {

    /**
     * Creates an {@link AuditorAware} bean to provide auditing functionality.
     * This bean is used to determine the current user responsible for
     * creating or modifying entities.
     *
     * @return an instance of {@link ApplicationAuditAware}
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * Creates a {@link CorsFilter} bean to handle Cross-Origin Resource Sharing (CORS).
     * This configuration allows requests from a specified frontend application
     * and defines permitted HTTP methods and headers.
     *
     * @return a configured {@link CorsFilter} instance
     */
    @Bean
    public CorsFilter corsFilter() {
       final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         final CorsConfiguration config = new CorsConfiguration();

            // Allow credentials for authentication
            config.setAllowCredentials(true);

            // Define allowed origins (update as necessary for production)
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));

            // Define allowed headers
            config.setAllowedHeaders(
                    Arrays.asList(
                            HttpHeaders.ORIGIN,
                            HttpHeaders.ACCEPT,
                            HttpHeaders.CONTENT_TYPE,
                            HttpHeaders.AUTHORIZATION
                    ));

            // Define allowed HTTP methods
            config.setAllowedMethods(
                    Arrays.asList(
                            "GET",
                            "POST",
                            "PUT",
                            "PATCH",
                            "DELETE"
                    )
            );

            // Register CORS configuration for all endpoints
            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
    }
}