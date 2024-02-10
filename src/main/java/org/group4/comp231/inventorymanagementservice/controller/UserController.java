package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.group4.comp231.inventorymanagementservice.dto.user.KeycloakTokenResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "/user")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "User Management", description = "Endpoints for User Management")
public class UserController {

    @GetMapping
    public String testEndpoint() {
        WebClient client = WebClient.builder()
                .baseUrl("https://ronaldjro.dev:8443/realms/Com231_GroupProject/protocol/openid-connect/token")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        KeycloakTokenResponseDto token = client.post()
                        .body(BodyInserters.fromFormData("client_id", System.getenv("CLIENT"))
                                .with("client_secret", System.getenv("CLIENT_SECRET"))
                                .with("scope", "email")
                                .with("grant_type", "client_credentials"))
                        .retrieve()
                        .bodyToFlux(KeycloakTokenResponseDto.class)
                        .blockLast();
        return token.getAccessToken();
    }
}
