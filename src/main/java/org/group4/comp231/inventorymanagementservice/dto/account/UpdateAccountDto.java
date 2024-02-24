package org.group4.comp231.inventorymanagementservice.dto.account;

import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Account}
 */
public record UpdateAccountDto(Long accountType, AddressDto address, @Size(max = 255) String label,
                               @Size(max = 255) String email) implements Serializable {
}