package org.group4.comp231.inventorymanagementservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

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

    @Size(max = 255)
    @Column(name = "primary_email")
    private String primaryEmail;

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Long getId() {
        return this.id;
    }

    public @Size(max = 255) @NotNull String getLabel() {
        return this.label;
    }

    public @NotNull Instant getCreatedAt() {
        return this.createdAt;
    }

    public @Size(max = 255) @NotNull String getCreatedBy() {
        return this.createdBy;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public @Size(max = 255) String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setLabel(@Size(max = 255) @NotNull String label) {
        this.label = label;
    }

    public void setCreatedAt(@NotNull Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(@Size(max = 255) @NotNull String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedBy(@Size(max = 255) String updatedBy) {
        this.updatedBy = updatedBy;
    }

}