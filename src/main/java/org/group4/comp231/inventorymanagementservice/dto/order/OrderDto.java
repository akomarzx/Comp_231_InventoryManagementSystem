package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Order}
 */
public record OrderDto(@NotNull @Size(max = 255) String orderReferenceNumber,
                       @NotNull Long orderType, @NotNull Long account, @Size(max = 255) String notes, Long warehouse,
                       @NotNull Set<OrderItemDTO> orderItems) implements Serializable {
}