package com.glowin.models.Input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResendEmailRequest(
        @NotBlank
        @Email
        String email
) {}
