package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestUserDto {

    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "Username must not exceed 125 characters")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "The username must only contain letters, numbers, spaces or hyphens."
    )
    private String username;

    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "Fullname must not exceed 125 characters")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "The fullname must only contain letters, numbers, spaces or hyphens."
    )
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Pattern(
            regexp = "^(USER|ADMIN)$",
            message = "Role must be either 'USER' or 'ADMIN'"
    )
    private String role;

}
