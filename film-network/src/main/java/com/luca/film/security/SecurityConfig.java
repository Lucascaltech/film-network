package com.luca.film.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class for the application.
 * This class defines security rules, including authentication and authorization settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * - Disables CSRF protection
     * - Enables CORS with default settings
     * - Permits access to API documentation and authentication endpoints
     * - Requires authentication for all other requests
     * - Configures OAuth2 resource server with JWT authentication
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while configuring security settings
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Enable CORS with default settings
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                        ).permitAll() // Allow public access to documentation and authentication endpoints
                                .anyRequest().authenticated() // Require authentication for other requests
                )
                .oauth2ResourceServer(auth ->
                        auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))); // Configure OAuth2 with JWT authentication
        return http.build();
    }
}
