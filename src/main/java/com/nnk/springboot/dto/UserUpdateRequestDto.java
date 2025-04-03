package com.nnk.springboot.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * For entering a user's modification form
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserUpdateRequestDto extends CommonRequestUserDto{

    private Integer id;

    /*
    Attention, ici, le mot de passe n'est pas obligatoire car on le modifie que si il est saisi => ^$| (vide ou ...)
    voir UserCreateRequestDto pour commentaires sur le reste.
    */
    @Pattern(
            regexp = "^$|^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one number and one special character."
    )
    private String password;

}
