package com.craftsoft.callDetailRecord.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sun.net.httpserver.Authenticator;

public enum StatusEnum {
    SUCCESS("success"),
    FAILED("failed");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static StatusEnum fromString(String value) {
        return value == null
                ? null
                : StatusEnum.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getKey() {
        return value;
    }
}

