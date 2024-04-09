package org.group4.comp231.inventorymanagementservice.dto.order;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.annotation.ValidateCodeID;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.util.List;

public record CreateOrderDTO(@NotNull(groups = Create.class) Long accountId,
                             @NotNull(groups = Create.class) @ValidateCodeID(codeTypeName = "ORDERTYPE") Long orderType,
                             String notes,
                             Long warehouseId,
                             @NotNull List<OrderItemDTO> orderItems) {
}
