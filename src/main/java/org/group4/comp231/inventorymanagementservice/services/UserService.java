package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.dto.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final KeycloakClientService keycloakClientService;

    public UserService(KeycloakClientService keycloakClientService) {
        this.keycloakClientService = keycloakClientService;
    }

    public void registerNewUser(UserRegistrationDto dto, Long tenantId) throws Exception {
        keycloakClientService.registerNewUser(dto, tenantId);
    }


}




















