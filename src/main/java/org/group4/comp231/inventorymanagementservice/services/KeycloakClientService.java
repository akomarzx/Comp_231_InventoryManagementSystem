package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.group4.comp231.inventorymanagementservice.config.KeycloakClientConfig;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeValue;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserUpdateDto;
import org.keycloak.admin.client.resource.UserResource;
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
                throw new Exception("Failed to register new user." + "IAM StatusCode: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserRepresentation> getAllUsersByTenant(@NotNull Long tenantId) {

        UsersResource usersResource = this.clientConfig.usersResource();
        List<UserRepresentation> allUserByTenant = usersResource.searchByAttributes(0, 10, true, true, "tenant_id:" + tenantId);

        for (UserRepresentation user : allUserByTenant) {
            List<GroupRepresentation> groups = usersResource.get(user.getId()).groups();
            user.setGroups(groups.stream().map(GroupRepresentation::getName).toList());
        }

        return allUserByTenant;
    }

    public void updateUser(@NotNull String userId, @NotNull UserUpdateDto userUpdateDto, Long tenantId) {
        UsersResource usersResource = this.clientConfig.usersResource();

        UserRepresentation updateRepresentation = new UserRepresentation();

        if (userUpdateDto.getFirstName() != null) {
            updateRepresentation.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            updateRepresentation.setLastName(userUpdateDto.getLastName());
        }

        updateRepresentation.setGroups(getGroupNameByCode(userUpdateDto.getGroupCodes()));
        UserResource userResource = usersResource.get(userId);
        userResource.update(updateRepresentation);

        this.updateUserGroups(this.getGroupNameByCode(userUpdateDto.getGroupCodes()), userResource);
    }

    public void deleteUser(@NotNull String userId) {

        UsersResource usersResource = this.clientConfig.usersResource();

        usersResource.get(userId).remove();

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
            keycloakUserRepresentationDto.setGroups(List.of("administrator_group"));
        } else {
            keycloakUserRepresentationDto.setGroups(getGroupNameByCode(dto.getGroupCodes()));
        }

        keycloakUserRepresentationDto.setAttributes(Map.of("tenant_id", Collections.singletonList(tenantId.toString())));

        return keycloakUserRepresentationDto;
    }

    private void updateUserGroups(List<String> groupNames, UserResource userResource) {
        List<GroupRepresentation> existingGroupsInRealm = this.clientConfig.realmRepresentation().groups().groups();
        List<GroupRepresentation> usersExistingGroups = userResource.groups();

        for (GroupRepresentation representation : usersExistingGroups) {
            if (groupNames.stream().noneMatch(names -> names.equals(representation.getName()))) {
                userResource.leaveGroup(representation.getId());
            }
        }

        for (String groupName : groupNames) {
            userResource.joinGroup(existingGroupsInRealm.stream()
                            .filter(groupRepresentation -> groupRepresentation.getName().equals(groupName))
                            .findFirst().get().getId());
        }
    }

    private List<String> getGroupNameByCode(List<Long> codeValueIdList) {
        List<String> groupNames = new ArrayList<>();
        for (Long groupCode : codeValueIdList) {
            CodeValue value = this.staticCodeService.getGCodeValueListUsingCodeBookID(this.staticCodeService.CODEBOOK_GROUP_ID).get().getCodeValues().stream()
                    .filter(cv -> Objects.equals(cv.getId(), groupCode))
                    .findAny().get();

            groupNames.add(value.getLabel());
        }

        return groupNames;
    }
}
