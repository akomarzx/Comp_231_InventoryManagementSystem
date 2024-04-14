package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO representation of data that is used to process or move the sales order into different stages
 * @param processCodeValueId Value for which stage of the sales order that is currently being performed
 * @param orderItems
 */
public record ProcessOrderDTO(
        @NotNull Long processCodeValueId,
        @NotEmpty List<OrderItemDTO> orderItems
) {
}
