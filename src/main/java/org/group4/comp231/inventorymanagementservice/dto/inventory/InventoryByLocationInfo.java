package org.group4.comp231.inventorymanagementservice.dto.inventory;

import org.group4.comp231.inventorymanagementservice.dto.warehouse.WarehouseLabelInfo;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Inventory}
 */
public interface InventoryByLocationInfo {

    Long getId();

    Long getQuantity();

    ProductInfo getProduct();

    WarehouseLabelInfo getWarehouse();

    /**
     * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Product}
     */
    interface ProductInfo {
        Long getId();
    }
}