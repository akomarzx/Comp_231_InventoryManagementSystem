package org.group4.comp231.inventorymanagementservice.dto.user;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link org.keycloak.representations.idm.UserRepresentation}
 */
public record UserSummaryInfoDto(String id, Long createdTimestamp, String username, String firstName, String lastName,
                                 String email, List<String> groups) implements Serializable {
}