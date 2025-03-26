package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 125)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(length = 125)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(length = 125)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(length = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;
}
