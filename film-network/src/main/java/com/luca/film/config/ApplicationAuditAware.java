package com.luca.film.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} to provide the current authenticated user.
 * This is used for auditing purposes to track who created or modified an entity.
 */
public class ApplicationAuditAware implements AuditorAware<String> {

    /**
     * Retrieves the currently authenticated user.
     *
     * @return an {@link Optional} containing the current user's username, or an empty {@link Optional} if no user is authenticated.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable(authentication.getName());
    }
}
