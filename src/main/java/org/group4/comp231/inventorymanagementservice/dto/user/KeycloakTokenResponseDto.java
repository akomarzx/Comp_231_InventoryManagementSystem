package org.group4.comp231.inventorymanagementservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class KeycloakTokenResponseDto implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("not-before-policy")
    private Long notBeforePolicy;

    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }
}
