package org.group4.comp231.inventorymanagementservice.dto.address;

import jakarta.validation.constraints.Size;
import org.group4.comp231.inventorymanagementservice.annotation.ValidateCodeID;

import java.io.Serializable;

/**
 * DTO for {@link org.group4.comp231.inventorymanagementservice.domain.Address}
 */
public record UpdateAddressDto(@Size(max = 255) String addressLine1, @Size(max = 255) String addressLine2,
                               @Size(max = 255) String city, @ValidateCodeID(codeTypeName = "province") Long province,
                               @ValidateCodeID(codeTypeName = "country") Long country, @Size(max = 255) String primaryPhone) implements Serializable {
}