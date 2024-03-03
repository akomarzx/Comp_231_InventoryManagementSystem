package org.group4.comp231.inventorymanagementservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.domain.Category;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
public record CategoryDto(@NotNull(groups = Create.class) @Size(max = 255) String label,
                          @Size(max = 255) String description) implements Serializable {
}