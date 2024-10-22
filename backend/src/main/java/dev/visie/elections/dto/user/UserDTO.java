package dev.visie.elections.dto.user;

import dev.visie.elections.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    String username;
    String email;
}
