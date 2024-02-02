package org.group4.comp231.inventorymanagementservice.dto.tenant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Tenant}
 */
public record CreateUpdateTenantDTO(Long id,
                                    @NotNull @Size(max = 255) @NotEmpty @NotBlank String label
                                    ) implements Serializable {
}