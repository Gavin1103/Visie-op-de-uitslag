package dev.visie.elections.dto.user;

import dev.visie.elections.model.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for updating an existing user.
 * Used to transfer user update data between client and server.
 */
@Getter
@Setter
public class UpdateUserDTO {
    private String username;
    private String email;
    private RoleEnum roleName;
    private String password;
}