package dev.visie.elections.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
}
