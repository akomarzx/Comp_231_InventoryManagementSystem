package org.group4.comp231.inventorymanagementservice.dto.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;
import org.group4.comp231.inventorymanagementservice.dto.address.UpdateAddressDto;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Warehouse}
 */
public record UpdateWarehouseDto(@Size(max = 255) String label, @Valid UpdateAddressDto address) implements Serializable {
}