package org.group4.comp231.inventorymanagementservice.domain.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.TenantId;

import java.time.Instant;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    @TenantId
    private Long tenant;

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTenant(@NotNull Long tenant) {
        this.tenant = tenant;
    }

    public void setLabel(@Size(max = 255) @NotNull String label) {
        this.label = label;
    }

    public void setDescription(@Size(max = 255) String description) {
        this.description = description;
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

    public Long getId() {
        return this.id;
    }

    public @NotNull Long getTenant() {
        return this.tenant;
    }

    public @Size(max = 255) @NotNull String getLabel() {
        return this.label;
    }

    public @Size(max = 255) String getDescription() {
        return this.description;
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
}