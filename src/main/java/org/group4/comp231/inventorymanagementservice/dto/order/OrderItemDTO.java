package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @param id  Represents line items in sales/purchase order.
 *            When creating order this is id for inventory item when updating this represents the order item id
 * @param quantity
 */
public record OrderItemDTO(@NotNull Long id, @NotNull Integer quantity) {
}
