package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.KeycloakClientConfig;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakClientService {

    private static final Log log = LogFactory.getLog(KeycloakClientService.class);

    private final KeycloakClientConfig clientConfig;

    public KeycloakClientService(KeycloakClientConfig config) {
        this.clientConfig = config;
    }

    public void registerNewUser(UserRegistrationDto dto, Long tenantId) {

        UsersResource usersResource = this.clientConfig.keycloak().realm(clientConfig.getRealm()).users();
        UserRepresentation userRepresentation = mapNewUserRepresentationDTO(dto, tenantId);

        try(Response response = usersResource.create(userRepresentation)) {
            log.info("Creating new User: " + response.getStatus());
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new Exception("Failed to register new user.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserRepresentation mapNewUserRepresentationDTO(@NotNull UserRegistrationDto dto, @NotNull Long tenantId) {

        UserRepresentation keycloakUserRepresentationDto = new UserRepresentation();

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(dto.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        keycloakUserRepresentationDto.setUsername(dto.getUsername());
        keycloakUserRepresentationDto.setEmail(dto.getEmail());
        keycloakUserRepresentationDto.setEnabled(true);
        keycloakUserRepresentationDto.setFirstName(dto.getFirstName());
        keycloakUserRepresentationDto.setLastName(dto.getLastName());
        keycloakUserRepresentationDto.setCredentials(List.of(credentialRepresentation));
        keycloakUserRepresentationDto.setGroups(List.of("administrator_group", "users"));
        keycloakUserRepresentationDto.setAttributes(Map.of("tenant_id", Collections.singletonList(tenantId.toString())));

        return keycloakUserRepresentationDto;
    }
}
