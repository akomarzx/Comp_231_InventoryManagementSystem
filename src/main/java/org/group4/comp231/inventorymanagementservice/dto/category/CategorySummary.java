package org.group4.comp231.inventorymanagementservice.dto.category;

import org.group4.comp231.inventorymanagementservice.domain.Category;

/**
 * Projection for {@link Category}
 */
public interface CategorySummary {
    Long getId();

    String getLabel();

    String getDescription();
}