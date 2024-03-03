package org.group4.comp231.inventorymanagementservice.dto.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Warehouse}
 */
public record WarehouseDto(@NotNull(groups = Create.class) @Size(max = 255) String label,
                           @Valid @NotNull(groups = Create.class) AddressDto address) implements Serializable {
}