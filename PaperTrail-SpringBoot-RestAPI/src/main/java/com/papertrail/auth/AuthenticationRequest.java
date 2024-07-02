package com.papertrail.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid") // null is considered valid that,s why we need @NotBlank
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;
}
