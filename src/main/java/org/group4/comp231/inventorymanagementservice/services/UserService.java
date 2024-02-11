package org.group4.comp231.inventorymanagementservice.services;

import jakarta.validation.constraints.NotNull;
import org.group4.comp231.inventorymanagementservice.dto.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String baseOauthIssuerUri;

    private final String adminRestAPIBaseURI = "https://ronaldjro.dev:8443/admin/realms/Com231_GroupProject";

    public void registerNewTenant(UserRegistrationDto dto, Long tenantId) throws Exception {

        boolean result = false;

        try {

            String token = this.getAccessToken();

            sendRequestToRegisterNewUser(dto, token, tenantId);

        } catch (WebClientResponseException e) {
            throw new Exception(e.getResponseBodyAsString());
        }

    }

    private void sendRequestToRegisterNewUser(UserRegistrationDto dto, String token, Long tenantId) {

        String registrationEndpoint = this.adminRestAPIBaseURI + "/users";
        KeycloakUserRepresentationDto userRepresentation = this.mapNewUserRepresentationDTO(dto, tenantId);

        WebClient client = WebClient.builder()
                .baseUrl(registrationEndpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

       client.post()
                .header("Authorization", "Bearer " + token )
                .bodyValue(userRepresentation)
                .retrieve()
                .bodyToFlux(String.class)
                .blockLast();

    }

    private String getAccessToken() {

        String tokenURI = baseOauthIssuerUri + "/protocol/openid-connect/token";

        WebClient client = WebClient.builder()
                .baseUrl(tokenURI)
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

        if (token != null && token.getAccessToken() != null) {
            return token.getAccessToken();
        } else {
            throw new ServerErrorException("Failed to obtain token", null);
        }
    }

    private KeycloakUserRepresentationDto mapNewUserRepresentationDTO(@NotNull UserRegistrationDto dto, @NotNull Long tenantId) {

        KeycloakUserRepresentationDto keycloakUserRepresentationDto = new KeycloakUserRepresentationDto();

        List<NewCredentialDto> credentialDtoList = new ArrayList<>();
        credentialDtoList.add(new NewCredentialDto("password", dto.getPassword(), false));

        keycloakUserRepresentationDto.setUsername(dto.getUsername());
        keycloakUserRepresentationDto.setEmail(dto.getEmail());
        keycloakUserRepresentationDto.setEnabled(true);
        keycloakUserRepresentationDto.setFirstName(dto.getFirstName());
        keycloakUserRepresentationDto.setLastName(dto.getLastName());
        keycloakUserRepresentationDto.setCredentialDtos(credentialDtoList);
        keycloakUserRepresentationDto.setGroups(List.of("administrator"));
        keycloakUserRepresentationDto.setAttributes(Map.of("tenant_id", tenantId.toString()));

        return keycloakUserRepresentationDto;
    }
}




















