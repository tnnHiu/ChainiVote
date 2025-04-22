package com.chainivote.chainivoteserver.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
