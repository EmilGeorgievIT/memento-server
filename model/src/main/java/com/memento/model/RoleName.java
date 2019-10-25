package com.memento.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoleName {
    ADMIN("ADMIN"),
    AGENCY("AGENCY"),
    BUYER("BUYER");

    private final String roleName;

    RoleName(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}