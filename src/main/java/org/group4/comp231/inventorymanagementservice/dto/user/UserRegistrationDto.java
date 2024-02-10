package org.group4.comp231.inventorymanagementservice.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class UserRegistrationDto implements Serializable {

    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

}
