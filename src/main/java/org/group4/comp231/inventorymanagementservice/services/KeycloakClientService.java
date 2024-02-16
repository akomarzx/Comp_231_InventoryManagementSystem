package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.KeycloakClientConfig;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeValue;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeycloakClientService {

    private static final Log log = LogFactory.getLog(KeycloakClientService.class);

    private final KeycloakClientConfig clientConfig;
    private final StaticCodeService staticCodeService;

    public KeycloakClientService(KeycloakClientConfig config, StaticCodeService staticCodeService) {
        this.clientConfig = config;
        this.staticCodeService = staticCodeService;
    }

    public void registerNewUser(UserRegistrationDto dto, Long tenantId, boolean isNewCustomerRegistration) {

        UsersResource usersResource = this.clientConfig.usersResource();
        UserRepresentation userRepresentation = mapNewUserRepresentationDTO(dto, tenantId, isNewCustomerRegistration);

        try(Response response = usersResource.create(userRepresentation)) {
            log.info("Creating new User: " + response.getStatus());
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new Exception("Failed to register new user.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserRepresentation> getAllUsersByTenant(@NotNull Long tenantId) {

        List<UserRepresentation> allUserByTenant = this.clientConfig.usersResource().searchByAttributes(0, 10, true, true, "tenant_id:" + tenantId);
        UsersResource resource = this.clientConfig.keycloak().realm(clientConfig.getRealm()).users();

        for (UserRepresentation user : allUserByTenant) {
            List<GroupRepresentation> groups = resource.get(user.getId()).groups();
            user.setGroups(groups.stream().map(GroupRepresentation::getName).toList());
        }

        return allUserByTenant;
    }


    private UserRepresentation mapNewUserRepresentationDTO(@NotNull UserRegistrationDto dto, @NotNull Long tenantId, boolean newCustomerRegistration) {

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

        if (newCustomerRegistration) {
            keycloakUserRepresentationDto.setGroups(List.of("users", "administrator_group"));
        } else {
            List<String> groupName = new ArrayList<>();
            for (Long groupCode : dto.getGroupCodes()) {
                CodeValue value = this.staticCodeService.getAllGroupsCode().get().getCodeValues().stream()
                        .filter(cv -> Objects.equals(cv.getId(), groupCode))
                        .findAny().get();

                groupName.add(value.getLabel());
            }
            keycloakUserRepresentationDto.setGroups(groupName);
        }

        keycloakUserRepresentationDto.setAttributes(Map.of("tenant_id", Collections.singletonList(tenantId.toString())));

        return keycloakUserRepresentationDto;
    }
}
