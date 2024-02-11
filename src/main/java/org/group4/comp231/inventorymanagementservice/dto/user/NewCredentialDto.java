package org.group4.comp231.inventorymanagementservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NewCredentialDto implements Serializable {

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    @JsonProperty("temporary")
    private boolean temporary;

    public NewCredentialDto(String type, String value, boolean temporary) {
        this.type = type;
        this.value = value;
        this.temporary = temporary;
    }

    public NewCredentialDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
}
