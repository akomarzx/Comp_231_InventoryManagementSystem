package org.group4.comp231.inventorymanagementservice.dto.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.annotation.ValidateCodeID;
import org.group4.comp231.inventorymanagementservice.dto.address.CreateAddressDto;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Account}
 */
public record CreateAccountDto(@NotNull @ValidateCodeID(codeTypeName = "accountType") Long accountType,
                               @Valid @NotNull CreateAddressDto address, @NotNull @Size(max = 255) String label,
                               @NotNull @Size(max = 255) String email) implements Serializable {
}