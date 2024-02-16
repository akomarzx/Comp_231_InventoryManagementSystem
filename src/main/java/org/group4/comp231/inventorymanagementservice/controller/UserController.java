package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.group4.comp231.inventorymanagementservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/public/user")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "User Management", description = "Endpoints for User Management")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

}
