package org.group4.comp231.inventorymanagementservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tenant", indexes = {
        @Index(name = "label", columnList = "label", unique = true)
})
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;

}