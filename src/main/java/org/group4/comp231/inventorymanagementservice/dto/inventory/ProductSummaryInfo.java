package org.group4.comp231.inventorymanagementservice.dto.inventory;

import org.group4.comp231.inventorymanagementservice.domain.Category;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Product}
 */
public interface ProductSummaryInfo {

    Long getId();

    Long getUpi();

    BigDecimal getPrice();

    String getLabel();

    String getImageUrl();

    Set<CategoryInfo> getProductCategories();

    String getSku();
    /**
     * Projection for {@link Category}
     */
    interface CategoryInfo {
        Long getId();

        String getLabel();

        String getDescription();
    }
}