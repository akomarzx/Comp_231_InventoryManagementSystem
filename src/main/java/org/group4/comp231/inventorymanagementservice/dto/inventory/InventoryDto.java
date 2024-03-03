package org.group4.comp231.inventorymanagementservice.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Update;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Inventory}
 */
public record InventoryDto(Long inventoryId,
                           @NotNull(groups = Create.class) ProductDto product,
                           @NotNull(groups = Create.class) Long warehouse, @Min(value = 0L) @NotNull(groups = Create.class) Long quantity,
                           @Min(value = 0L) Long minimumQuantity, @Min(value = 0L) Long maximumQuantity, @Size(max = 255) String notes) implements Serializable {
}