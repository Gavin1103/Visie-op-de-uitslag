package dev.visie.elections.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN,
    PARTYMEMBER,
    USER,
    VERIFIED;

    @Override
    public String getAuthority() {
        return name();
    }
}