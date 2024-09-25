package dev.visie.elections.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    @JsonProperty("username")
    private String username;
    @JsonProperty("confirmation_token")
    private String confirmationToken;
}