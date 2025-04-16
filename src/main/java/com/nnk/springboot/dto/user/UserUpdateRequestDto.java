package com.nnk.springboot.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(max = 125, message = "{validation.password.maxlength}")
    /*
    Attention, ici, le mot de passe n'est pas obligatoire car on le modifie que si il est saisi => ^$| (vide ou ...)
    voir UserCreateRequestDto pour commentaires sur le reste.
    */
    @Pattern(
            regexp = "^$|^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "{validation.password.pattern}"
    )
    private String password;

}
