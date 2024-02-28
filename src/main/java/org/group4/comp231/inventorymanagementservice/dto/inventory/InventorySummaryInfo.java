package org.group4.comp231.inventorymanagementservice.dto.inventory;

import org.group4.comp231.inventorymanagementservice.dto.inventory.ProductSummaryInfo;
import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseLabelInfo;

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
}