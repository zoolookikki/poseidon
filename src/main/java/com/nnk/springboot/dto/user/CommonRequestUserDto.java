package com.nnk.springboot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class CommonRequestUserDto {

    @NotBlank(message = "{validation.required}")
    @Size(max = 125, message = "{validation.max.length}")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "{validation.pattern.username}"
    )
    private String username;

    @NotBlank(message = "{validation.required}")
    @Size(max = 125, message = "{validation.max.length}")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9 -]+$",
            message = "{validation.pattern.fullname}"
    )
    private String fullname;

    @NotBlank(message = "{validation.required}")
    @Pattern(
            regexp = "^(USER|ADMIN)$",
            message = "{validation.pattern.role}"
    )
    private String role;

}
