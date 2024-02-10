package org.group4.comp231.inventorymanagementservice.dto.tenant;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Tenant}
 */
public record TenantDTO(Long id, String label, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) implements Serializable {
}