package org.group4.comp231.inventorymanagementservice.dto.category;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.category.Category}
 */
public record CategoryDto(Long id, String label, String description) implements Serializable {
}