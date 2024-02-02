package org.group4.comp231.inventorymanagementservice.dto.tenant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Tenant}
 */
public record TenantDTO(Long id, @NotNull @Size(max = 255) String label, @NotNull Instant createdAt,
                        @NotNull @Size(max = 255) String createdBy, Instant updatedAt,
                        @Size(max = 255) String updatedBy) implements Serializable {
}