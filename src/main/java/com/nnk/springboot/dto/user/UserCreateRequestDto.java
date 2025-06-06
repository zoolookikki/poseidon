package com.nnk.springboot.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * For entering the user creation form.
 */
/*
@Data génère automatiquement des getters, setters, `toString`, equals, hashCode, et un constructeur par défaut => warning.
Par défaut, Lombok génère equals() et hashCode() uniquement pour les champs de UserCreateRequestDto, et il ignore CommonRequestUserDto.
En ajoutant @EqualsAndHashCode(callSuper = true), il inclut les champs hérités de CommonUserDTO dans equals() et hashCode(), évitant ainsi des comparaisons incorrectes.
Idem pour toString().
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCreateRequestDto extends CommonRequestUserDto{

    @NotBlank(message = "{validation.required}")
    @Size(max = 125, message = "{validation.max.length}")
    /*
    (?=.*[A-Z]) : au moins une majuscule.
    (?=.*\\d) : au moins un chiffre.
    (?=.*[@#$%^&+=!]) : au moins un caractère spécial.
    .{8,}$ : minimum 8 caractères.
    */
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "{validation.pattern.password}"
    )
    private String password;

 }
