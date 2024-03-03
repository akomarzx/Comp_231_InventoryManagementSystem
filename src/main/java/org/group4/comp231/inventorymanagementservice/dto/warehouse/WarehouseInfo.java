package org.group4.comp231.inventorymanagementservice.dto.warehouse;

import org.group4.comp231.inventorymanagementservice.dto.address.AddressSummaryInfo;

/**
 * Projection for {@link org.group4.comp231.inventorymanagementservice.domain.Warehouse}
 */
public interface WarehouseInfo {
    Long getId();

    String getLabel();

    AddressSummaryInfo getAddress();
}