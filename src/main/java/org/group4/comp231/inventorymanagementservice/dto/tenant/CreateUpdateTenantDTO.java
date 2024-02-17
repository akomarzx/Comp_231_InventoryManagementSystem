package org.group4.comp231.inventorymanagementservice.dto.tenant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Tenant}
 */
public record CreateUpdateTenantDTO(String companyName, String email) implements Serializable { }