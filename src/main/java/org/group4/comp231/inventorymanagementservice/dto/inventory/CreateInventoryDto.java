package org.group4.comp231.inventorymanagementservice.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Inventory}
 */
public record CreateInventoryDto(Long productUpi, @NotNull BigDecimal productPrice, @NotNull String productLabel, String productImageUrl,
                                 @NotNull Set<Long> productCategoryIds, @NotNull @Size(max = 255) String sku,
                                 @NotNull Long warehouse, @Min(value = 0L) @NotNull Long quantity, @Min(value = 0L) Long minimumQuantity,
                                 @Min(value = 0L) Long maximumQuantity, @Size(max = 255) String notes) implements Serializable {
}