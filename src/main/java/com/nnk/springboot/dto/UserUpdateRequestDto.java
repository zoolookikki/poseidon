package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * For entering a user's modification form
 */
@Data
public class UserUpdateRequestDto {

    private Integer id;

    @NotBlank(message = "Username is mandatory")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "The username must only contain letters, numbers, spaces or hyphens."
    )
    private String username;

    /*
    Attention, ici, le mot de passe n'est pas obligatoire car on le modifie que si il est saisi => ^$| (vide ou ...)
    voir UserCreateRequestDto pour commentaires sur le reste.
    */
    @Pattern(
            regexp = "^$|^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one number and one special character."
    )
    private String password;

    @NotBlank(message = "Full name is mandatory")
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
