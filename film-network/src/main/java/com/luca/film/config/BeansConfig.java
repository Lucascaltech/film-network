package com.luca.film.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class BeansConfig {
//    private final UserDetailsService userDetailsService;

//    public BeansConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    /**
     * Create an authentication provider
     * @return the authentication provider
     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    /**
     * Create a password encoder
     * @return the password encoder
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /**
     *  Create an authentication manager
     *
     * @param configuration the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs while creating the authentication manager
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }

    /**
     * Create an auditor aware bean
     * @return the auditor aware bean
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * Create a cors filter
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
       final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         final CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedHeaders(
                    Arrays.asList(
                            HttpHeaders.ORIGIN,
                            HttpHeaders.ACCEPT,
                            HttpHeaders.CONTENT_TYPE,
                            HttpHeaders.AUTHORIZATION
                    ));
            config.setAllowedMethods(
                    Arrays.asList(
                            "GET",
                            "POST",
                            "PUT",
                            "PATCH",
                            "DELETE"
                    )
            );

            source.registerCorsConfiguration("/**", config);
            return new CorsFilter(source);
    }
}
