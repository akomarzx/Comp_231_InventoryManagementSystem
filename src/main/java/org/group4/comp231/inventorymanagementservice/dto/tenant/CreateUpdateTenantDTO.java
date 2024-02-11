package org.group4.comp231.inventorymanagementservice.dto.tenant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Tenant}
 */
public record CreateUpdateTenantDTO(Long id, @NotNull @NotBlank String companyName, @NotNull @NotBlank String email) implements Serializable { }