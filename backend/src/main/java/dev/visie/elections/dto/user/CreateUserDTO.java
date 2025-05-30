package dev.visie.elections.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.visie.elections.model.enums.RoleEnum;
import dev.visie.elections.model.enums.RoleEnumDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @JsonDeserialize(using = RoleEnumDeserializer.class)
    private RoleEnum roleName;
}