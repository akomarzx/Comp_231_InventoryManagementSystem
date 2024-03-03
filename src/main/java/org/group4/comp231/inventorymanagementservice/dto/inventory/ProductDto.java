package org.group4.comp231.inventorymanagementservice.dto.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Update;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Product}
 */
public record ProductDto(@NotNull(groups = Update.class) String sku,Long upi, @NotNull(groups = Create.class) BigDecimal price,
                         @NotNull(groups = Create.class) @Size(max = 255) String label,
                         @Size(max = 255) String imageUrl) implements Serializable {
}