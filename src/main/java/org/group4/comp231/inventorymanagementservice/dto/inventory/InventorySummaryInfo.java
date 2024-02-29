package org.group4.comp231.inventorymanagementservice.dto.inventory;

import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseLabelInfo;

import java.time.Instant;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Inventory}
 */
public interface InventorySummaryInfo {
    Long getId();

    String getSku();

    Long getQuantity();

    Long getMinimumQuantity();

    Long getMaximumQuantity();

    String getNotes();

    ProductSummaryInfo getProduct();

    WarehouseLabelInfo getWarehouse();

    Instant getCreatedAt();
}