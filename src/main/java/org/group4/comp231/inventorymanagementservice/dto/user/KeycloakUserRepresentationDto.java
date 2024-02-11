package org.group4.comp231.inventorymanagementservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class KeycloakUserRepresentationDto implements Serializable {

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("attributes")
    private Map<String, String> attributes;

    @JsonProperty("credentials")
    private List<NewCredentialDto> credentialDtos;

    @JsonProperty("groups")
    private List<String> groups;

    public KeycloakUserRepresentationDto(boolean enabled, String firstName, String lastName, String email, Map<String, String> attributes, List<NewCredentialDto> credentialDtos, List<String> groups, String username) {
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.attributes = attributes;
        this.credentialDtos = credentialDtos;
        this.groups = groups;
        this.username = username;
    }

    public KeycloakUserRepresentationDto() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<NewCredentialDto> getCredentialDtos() {
        return credentialDtos;
    }

    public void setCredentialDtos(List<NewCredentialDto> credentialDtos) {
        this.credentialDtos = credentialDtos;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }
}
