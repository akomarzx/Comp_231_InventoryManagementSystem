package org.group4.comp231.inventorymanagementservice.dto.account;

import org.group4.comp231.inventorymanagementservice.dto.address.AddressDto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Account}
 */
public record AccountDto(Long id, String label, String email, Long accountType, AddressDto address,
                         Instant createdAt, String createdBy, Instant updatedAt,
                         String updatedBy) implements Serializable {
}