package com.luca.film.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity is a superclass for all entities providing common fields for auditing.
 * It includes fields for ID, creation and modification timestamps, and user tracking.
 * This class uses Spring Data JPA auditing to automatically populate these fields.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * Unique identifier for the entity.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Timestamp when the entity was created.
     * Automatically set by Spring Data JPA auditing.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the entity was last modified.
     * Automatically updated by Spring Data JPA auditing.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;

    /**
     * Username of the user who created the entity.
     * Automatically set by Spring Data JPA auditing.
     */
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    /**
     * Username of the user who last modified the entity.
     * Automatically updated by Spring Data JPA auditing.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
}