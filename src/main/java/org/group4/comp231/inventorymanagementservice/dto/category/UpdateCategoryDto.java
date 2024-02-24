package org.group4.comp231.inventorymanagementservice.dto.category;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.category.Category}
 */
public record UpdateCategoryDto(@Size(max = 255) String label,
                                @Size(max = 255) String description) implements Serializable {
}