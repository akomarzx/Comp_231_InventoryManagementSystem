package org.group4.comp231.inventorymanagementservice.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Warehouse}
 */
public record CreateWarehouseDto(@NotNull @Size(max = 255) String label,
                                 @NotNull AddressDto address) implements Serializable {
}