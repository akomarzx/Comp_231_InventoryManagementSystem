package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(@NotNull Long id, @NotNull Integer quantity) {
}
