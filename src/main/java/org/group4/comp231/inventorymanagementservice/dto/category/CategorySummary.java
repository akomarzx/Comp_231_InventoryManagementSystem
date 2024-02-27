package org.group4.comp231.inventorymanagementservice.dto.category;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.category.Category}
 */
public interface CategorySummary {
    Long getId();

    String getLabel();

    String getDescription();
}