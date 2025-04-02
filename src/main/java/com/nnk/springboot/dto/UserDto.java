package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * For displaying the User without password.
 */
@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String username;
    private String fullname;
    private String role;

}
