package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.group4.comp231.inventorymanagementservice.dto.user.KeycloakTokenResponseDto;
import org.group4.comp231.inventorymanagementservice.dto.user.UserRegistrationDto;
import org.group4.comp231.inventorymanagementservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "/user")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "User Management", description = "Endpoints for User Management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
