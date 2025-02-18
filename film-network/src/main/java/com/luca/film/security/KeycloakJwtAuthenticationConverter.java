package com.luca.film.security;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom JWT authentication converter for Keycloak.
 * This class extracts roles from the Keycloak JWT token and converts them into Spring Security authorities.
 */
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /**
     * Converts a JWT token into an {@link AbstractAuthenticationToken}.
     * The method extracts granted authorities from both the default JWT claims
     * and the Keycloak-specific resource access rules.
     *
     * @param source the JWT token
     * @return an {@link AbstractAuthenticationToken} with extracted authorities
     */
    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRules(source).stream()
                ).collect(Collectors.toSet())
        );
    }

    /**
     * Extracts roles from the Keycloak-specific `resource_access` claim.
     * The method retrieves roles assigned under the "account" resource
     * and maps them to Spring Security {@link GrantedAuthority} objects.
     *
     * @param jwt the JWT token containing Keycloak resource access claims
     * @return a collection of granted authorities derived from Keycloak roles
     */
    private Collection<? extends GrantedAuthority> extractResourceRules(Jwt jwt) {
        var sourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        var external = (Map<String, List<String>>) sourceAccess.get("account");
        var roles = external.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(Collectors.toSet());
    }
}
