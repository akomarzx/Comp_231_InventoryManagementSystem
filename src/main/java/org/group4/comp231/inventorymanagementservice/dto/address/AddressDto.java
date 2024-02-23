package org.group4.comp231.inventorymanagementservice.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Address}
 */
public record AddressDto(@NotNull @Size(max = 255) String addressLine1, @Size(max = 255) String addressLine2,
                         @NotNull @Size(max = 255) String city, @NotNull Long province, @NotNull Long country,
                         @Size(max = 15) String primaryPhone) implements Serializable {
}