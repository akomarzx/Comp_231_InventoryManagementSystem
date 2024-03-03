package org.group4.comp231.inventorymanagementservice.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.annotation.ValidateCodeID;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Address}
 */
public record AddressDto(@NotNull(groups = Create.class) @Size(max = 255) String addressLine1, @Size(max = 255) String addressLine2,
                         @NotNull(groups = Create.class) @Size(max = 255) String city, @NotNull(groups = Create.class) @ValidateCodeID(codeTypeName = "province") Long province,
                         @NotNull(groups = Create.class) @ValidateCodeID(codeTypeName = "country") Long country, @Size(max = 255) String primaryPhone) implements Serializable {
}