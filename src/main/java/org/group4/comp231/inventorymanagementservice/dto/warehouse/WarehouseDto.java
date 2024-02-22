package org.group4.comp231.inventorymanagementservice.dto.warehouse;

import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.domain.Address;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Warehouse}
 */
public record WarehouseDto(@Size(max = 255) String label, AddressDto address) implements Serializable {
}