package com.nnk.springboot.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * For entering the user creation form.
 */
@Data
public class UserCreateRequestDto {

    @NotBlank(message = "Username is mandatory")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "The username must only contain letters, numbers, spaces or hyphens."
    )
    private String username;

    @NotBlank(message = "Password is mandatory")
    /*
    (?=.*[A-Z]) : au moins une majuscule.
    (?=.*\\d) : au moins un chiffre.
    (?=.*[@#$%^&+=!]) : au moins un caractère spécial.
    .{8,}$ : minimum 8 caractères.
    */
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one number and one special character."
    )
    private String password;

    @NotBlank(message = "FullName is mandatory")
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
