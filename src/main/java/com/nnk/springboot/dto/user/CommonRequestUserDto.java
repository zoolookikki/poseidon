package com.nnk.springboot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestUserDto {

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "Must only contain letters, numbers, spaces or hyphens."
    )
    private String username;

    @NotBlank(message = "This field is required")
    @Size(max = 125, message = "Maximum length: 125 characters")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "Must only contain letters, numbers, spaces or hyphens."
    )
    private String fullname;

    @NotBlank(message = "This field is required")
    @Pattern(
            regexp = "^(USER|ADMIN)$",
            message = "Must be either 'USER' or 'ADMIN'"
    )
    private String role;

}
