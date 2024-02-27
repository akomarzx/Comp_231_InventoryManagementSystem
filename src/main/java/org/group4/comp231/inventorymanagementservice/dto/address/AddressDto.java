package org.group4.comp231.inventorymanagementservice.dto.address;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Address}
 */
public record AddressDto(Long id, String addressLine1, String addressLine2, String city,
                         Long province, Long country, String primaryPhone) implements Serializable {
}